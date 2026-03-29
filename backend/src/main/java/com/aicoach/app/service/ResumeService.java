package com.aicoach.app.service;

import com.aicoach.app.model.Resume;
import com.aicoach.app.repository.ResumeRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Map<String, Object> uploadResume(String userEmail, MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate file type
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
                response.put("status", "error");
                response.put("message", "Only PDF files are allowed.");
                return response;
            }

            // Extract text from PDF using Apache PDFBox
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String extractedText = stripper.getText(document);
            document.close();

            if (extractedText == null || extractedText.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Could not extract text from PDF. Make sure it is not a scanned image.");
                return response;
            }

            // Save to database
            Resume resume = new Resume();
            resume.setUserEmail(userEmail);
            resume.setFileName(fileName);
            resume.setExtractedText(extractedText.trim());
            resume.setUploadedAt(LocalDateTime.now());
            resumeRepository.save(resume);

            response.put("status", "success");
            response.put("message", "Resume uploaded and parsed successfully!");
            response.put("fileName", fileName);
            response.put("extractedText", extractedText.trim());
            response.put("wordCount", extractedText.trim().split("\\s+").length);
            return response;

        } catch (IOException e) {
            response.put("status", "error");
            response.put("message", "Failed to process PDF: " + e.getMessage());
            return response;
        }
    }

    public Map<String, Object> getMyResume(String userEmail) {
        Map<String, Object> response = new HashMap<>();
        Optional<Resume> resume = resumeRepository.findTopByUserEmailOrderByUploadedAtDesc(userEmail);

        if (resume.isPresent()) {
            response.put("status", "success");
            response.put("fileName", resume.get().getFileName());
            response.put("extractedText", resume.get().getExtractedText());
            response.put("uploadedAt", resume.get().getUploadedAt().toString());
        } else {
            response.put("status", "not_found");
            response.put("message", "No resume uploaded yet. Please upload your resume first.");
        }
        return response;
    }

    public String getResumeText(String userEmail) {
        Optional<Resume> resume = resumeRepository.findTopByUserEmailOrderByUploadedAtDesc(userEmail);
        return resume.map(Resume::getExtractedText).orElse("");
    }
}

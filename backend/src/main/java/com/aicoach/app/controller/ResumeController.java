package com.aicoach.app.controller;

import com.aicoach.app.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:4200")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(resumeService.uploadResume(userEmail, file));
    }

    @GetMapping("/my-resume")
    public ResponseEntity<Map<String, Object>> getMyResume(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(resumeService.getMyResume(userEmail));
    }
}

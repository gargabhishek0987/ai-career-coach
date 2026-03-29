package com.aicoach.app.service;

import com.aicoach.app.model.CoachingSession;
import com.aicoach.app.repository.CoachingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoachService {

    @Autowired private GroqAIService groqAIService;
    @Autowired private ResumeService resumeService;
    @Autowired private CoachingSessionRepository sessionRepository;

    // ── 1. Interview Questions ─────────────────────────────────────────────
    public Map<String, Object> generateInterviewQuestions(String userEmail, String jobRole) {
        String aiResponse = groqAIService.generateInterviewQuestions(jobRole);
        saveSession(userEmail, "INTERVIEW", "Job Role: " + jobRole, aiResponse);
        return buildResponse("INTERVIEW", jobRole, aiResponse);
    }

    // ── 2. Skill Gap Analysis ──────────────────────────────────────────────
    public Map<String, Object> analyzeSkillGap(String userEmail, String jobDescription) {
        String resumeText = resumeService.getResumeText(userEmail);
        if (resumeText.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Please upload your resume first before running skill gap analysis.");
            return error;
        }
        String aiResponse = groqAIService.analyzeSkillGap(resumeText, jobDescription);
        saveSession(userEmail, "SKILL_GAP", jobDescription, aiResponse);
        return buildResponse("SKILL_GAP", jobDescription, aiResponse);
    }

    // ── 3. Job Role Suggestions ────────────────────────────────────────────
    public Map<String, Object> suggestJobRoles(String userEmail) {
        String resumeText = resumeService.getResumeText(userEmail);
        if (resumeText.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Please upload your resume first before getting job suggestions.");
            return error;
        }
        String aiResponse = groqAIService.suggestJobRoles(resumeText);
        saveSession(userEmail, "JOB_ROLES", "Based on resume", aiResponse);
        return buildResponse("JOB_ROLES", "Based on your resume", aiResponse);
    }

    // ── 4. Learning Roadmap ────────────────────────────────────────────────
    public Map<String, Object> generateRoadmap(String userEmail, String targetRole) {
        String aiResponse = groqAIService.generateRoadmap(targetRole);
        saveSession(userEmail, "ROADMAP", "Target Role: " + targetRole, aiResponse);
        return buildResponse("ROADMAP", targetRole, aiResponse);
    }

    // ── 5. Resume Tips ────────────────────────────────────────────────────
    public Map<String, Object> getResumeTips(String userEmail) {
        String resumeText = resumeService.getResumeText(userEmail);
        if (resumeText.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Please upload your resume first.");
            return error;
        }
        String aiResponse = groqAIService.improveResume(resumeText);
        saveSession(userEmail, "RESUME_TIPS", "Resume review", aiResponse);
        return buildResponse("RESUME_TIPS", "Resume improvement", aiResponse);
    }

    // ── History ───────────────────────────────────────────────────────────
    public List<CoachingSession> getHistory(String userEmail) {
        return sessionRepository.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }

    public Map<String, Object> getStats(String userEmail) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSessions", sessionRepository.findByUserEmailOrderByCreatedAtDesc(userEmail).size());
        stats.put("interviewSessions", sessionRepository.countByUserEmailAndSessionType(userEmail, "INTERVIEW"));
        stats.put("skillGapSessions", sessionRepository.countByUserEmailAndSessionType(userEmail, "SKILL_GAP"));
        stats.put("jobRoleSessions", sessionRepository.countByUserEmailAndSessionType(userEmail, "JOB_ROLES"));
        stats.put("roadmapSessions", sessionRepository.countByUserEmailAndSessionType(userEmail, "ROADMAP"));
        stats.put("resumeTipSessions", sessionRepository.countByUserEmailAndSessionType(userEmail, "RESUME_TIPS"));
        return stats;
    }

    // ── Helpers ───────────────────────────────────────────────────────────
    private void saveSession(String email, String type, String input, String response) {
        CoachingSession session = new CoachingSession();
        session.setUserEmail(email);
        session.setSessionType(type);
        session.setUserInput(input);
        session.setAiResponse(response);
        session.setCreatedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    private Map<String, Object> buildResponse(String type, String input, String aiResponse) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("sessionType", type);
        response.put("input", input);
        response.put("aiResponse", aiResponse);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}

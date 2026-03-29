package com.aicoach.app.controller;

import com.aicoach.app.model.CoachingSession;
import com.aicoach.app.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coach")
@CrossOrigin(origins = "http://localhost:4200")
public class CoachController {

    @Autowired
    private CoachService coachService;

    // 1. Interview Questions
    @PostMapping("/interview-questions")
    public ResponseEntity<Map<String, Object>> generateInterviewQuestions(
            @RequestBody Map<String, String> req,
            Authentication authentication) {
        String jobRole = req.get("jobRole");
        return ResponseEntity.ok(
            coachService.generateInterviewQuestions(authentication.getName(), jobRole));
    }

    // 2. Skill Gap Analysis
    @PostMapping("/skill-gap")
    public ResponseEntity<Map<String, Object>> analyzeSkillGap(
            @RequestBody Map<String, String> req,
            Authentication authentication) {
        String jobDescription = req.get("jobDescription");
        return ResponseEntity.ok(
            coachService.analyzeSkillGap(authentication.getName(), jobDescription));
    }

    // 3. Job Role Suggestions
    @GetMapping("/job-suggestions")
    public ResponseEntity<Map<String, Object>> suggestJobRoles(Authentication authentication) {
        return ResponseEntity.ok(coachService.suggestJobRoles(authentication.getName()));
    }

    // 4. Learning Roadmap
    @PostMapping("/roadmap")
    public ResponseEntity<Map<String, Object>> generateRoadmap(
            @RequestBody Map<String, String> req,
            Authentication authentication) {
        String targetRole = req.get("targetRole");
        return ResponseEntity.ok(
            coachService.generateRoadmap(authentication.getName(), targetRole));
    }

    // 5. Resume Tips
    @GetMapping("/resume-tips")
    public ResponseEntity<Map<String, Object>> getResumeTips(Authentication authentication) {
        return ResponseEntity.ok(coachService.getResumeTips(authentication.getName()));
    }

    // History & Stats
    @GetMapping("/history")
    public ResponseEntity<List<CoachingSession>> getHistory(Authentication authentication) {
        return ResponseEntity.ok(coachService.getHistory(authentication.getName()));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(Authentication authentication) {
        return ResponseEntity.ok(coachService.getStats(authentication.getName()));
    }
}

package com.aicoach.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqAIService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String callAI(String systemPrompt, String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0 Safari/537.36");

            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(systemMsg);
            messages.add(userMsg);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("max_tokens", 1500);
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> choices =
                        (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
            return "Sorry, I could not get a response from the AI.";
        } catch (Exception e) {
            return "AI service error: " + e.getMessage();
        }
    }

    // ── 5 AI Coach Features ────────────────────────────────────────────────

    public String generateInterviewQuestions(String jobRole) {
        String system = "You are an expert technical interviewer. Generate clear, relevant interview questions with brief answer hints.";
        String user = "Generate 10 interview questions for the role: " + jobRole +
                      ". Format each as: Q1. [Question]\nHint: [Brief answer hint]\n";
        return callAI(system, user);
    }

    public String analyzeSkillGap(String resumeText, String jobDescription) {
        String system = "You are a career advisor who analyzes skill gaps. Be specific and actionable.";
        String user = "Resume skills:\n" + resumeText +
                      "\n\nJob Description:\n" + jobDescription +
                      "\n\nAnalyze the skill gap. List: 1) Skills I have, 2) Missing skills, 3) How to fill each gap.";
        return callAI(system, user);
    }

    public String suggestJobRoles(String resumeText) {
        String system = "You are a career counselor who suggests suitable job roles based on skills.";
        String user = "Based on this resume/skills:\n" + resumeText +
                      "\n\nSuggest 8 suitable job roles with: Role name, Why it fits, Expected salary range, Growth potential.";
        return callAI(system, user);
    }

    public String generateRoadmap(String targetRole) {
        String system = "You are a learning coach who creates detailed, actionable learning roadmaps.";
        String user = "Create a complete learning roadmap to become a: " + targetRole +
                      ". Include: Phase name, Topics to learn, Resources (free + paid), Time estimate per phase.";
        return callAI(system, user);
    }

    public String improveResume(String resumeText) {
        String system = "You are a professional resume writer and career coach. Give specific, actionable improvements.";
        String user = "Review this resume and provide improvement tips:\n" + resumeText +
                      "\n\nProvide: 1) Strengths, 2) Weaknesses, 3) Specific rewrites for weak sections, 4) ATS optimization tips.";
        return callAI(system, user);
    }
}

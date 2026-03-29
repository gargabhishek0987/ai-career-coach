package com.aicoach.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coaching_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoachingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String sessionType; // INTERVIEW, SKILL_GAP, JOB_ROLES, ROADMAP, RESUME_TIPS

    @Column(columnDefinition = "TEXT")
    private String userInput;

    @Column(columnDefinition = "LONGTEXT")
    private String aiResponse;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

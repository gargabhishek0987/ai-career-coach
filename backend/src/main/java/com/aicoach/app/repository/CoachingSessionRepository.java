package com.aicoach.app.repository;

import com.aicoach.app.model.CoachingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachingSessionRepository extends JpaRepository<CoachingSession, Long> {
    List<CoachingSession> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    List<CoachingSession> findByUserEmailAndSessionTypeOrderByCreatedAtDesc(String userEmail, String sessionType);
    long countByUserEmailAndSessionType(String userEmail, String sessionType);
}

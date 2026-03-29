package com.aicoach.app.repository;

import com.aicoach.app.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findTopByUserEmailOrderByUploadedAtDesc(String userEmail);
}

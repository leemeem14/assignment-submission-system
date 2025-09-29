package com.assignment.system.repository;

import com.assignment.system.entity.Grade;
import com.assignment.system.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findBySubmission(Submission submission);
}

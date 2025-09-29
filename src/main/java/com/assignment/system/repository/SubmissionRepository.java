package com.assignment.system.repository;

import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.Submission;
import com.assignment.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudent(User student);
    List<Submission> findByAssignment(Assignment assignment);
    Optional<Submission> findByStudentAndAssignment(User student, Assignment assignment);
    List<Submission> findByAssignmentOrderBySubmittedAtDesc(Assignment assignment);
}

package com.assignment.system.repository;

import com.assignment.system.entity.Comment;
import com.assignment.system.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySubmissionOrderByCreatedAtAsc(Submission submission);
}

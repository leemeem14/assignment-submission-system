package com.assignment.system.service;

import com.assignment.system.dto.CommentDto;
import com.assignment.system.entity.Comment;
import com.assignment.system.entity.Submission;
import com.assignment.system.entity.User;
import com.assignment.system.repository.CommentRepository;
import com.assignment.system.repository.SubmissionRepository;
import com.assignment.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long submissionId, CommentDto dto, String authorUsername) {
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .author(author)
                .submission(submission)
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> findBySubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));
        return commentRepository.findBySubmissionOrderByCreatedAtAsc(submission);
    }
}

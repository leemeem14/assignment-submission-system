package com.assignment.system.service;

import com.assignment.system.dto.GradeDto;
import com.assignment.system.entity.Grade;
import com.assignment.system.entity.Submission;
import com.assignment.system.repository.GradeRepository;
import com.assignment.system.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public Grade gradeSubmission(Long submissionId, GradeDto dto) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));

        Optional<Grade> existingGrade = gradeRepository.findBySubmission(submission);

        Grade grade;
        if (existingGrade.isPresent()) {
            // 기존 채점 수정
            grade = existingGrade.get();
            grade.setScore(dto.getScore());
            grade.setFeedback(dto.getFeedback());
        } else {
            // 새로운 채점 생성
            grade = Grade.builder()
                    .score(dto.getScore())
                    .feedback(dto.getFeedback())
                    .submission(submission)
                    .build();
        }

        return gradeRepository.save(grade);
    }

    public Optional<Grade> findBySubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));
        return gradeRepository.findBySubmission(submission);
    }
}

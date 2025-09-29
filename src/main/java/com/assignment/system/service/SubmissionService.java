package com.assignment.system.service;

import com.assignment.system.dto.SubmissionDto;
import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.Submission;
import com.assignment.system.entity.User;
import com.assignment.system.repository.AssignmentRepository;
import com.assignment.system.repository.SubmissionRepository;
import com.assignment.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Submission submitAssignment(Long assignmentId, SubmissionDto dto, String studentUsername) throws IOException {
        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다"));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));

        // 기존 제출물이 있는지 확인
        Optional<Submission> existingSubmission = submissionRepository.findByStudentAndAssignment(student, assignment);

        Submission submission;
        if (existingSubmission.isPresent()) {
            // 기존 제출물 수정
            submission = existingSubmission.get();
            submission.setContent(dto.getContent());
        } else {
            // 새로운 제출물 생성
            submission = Submission.builder()
                    .content(dto.getContent())
                    .student(student)
                    .assignment(assignment)
                    .build();
        }

        // 파일 업로드 처리
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            String fileName = saveFile(dto.getFile());
            submission.setFileName(dto.getFile().getOriginalFilename());
            submission.setFilePath(fileName);
        }

        return submissionRepository.save(submission);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    public List<Submission> findByStudent(String studentUsername) {
        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다"));
        return submissionRepository.findByStudent(student);
    }

    public List<Submission> findByAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));
        return submissionRepository.findByAssignmentOrderBySubmittedAtDesc(assignment);
    }

    public Optional<Submission> findById(Long id) {
        return submissionRepository.findById(id);
    }
}

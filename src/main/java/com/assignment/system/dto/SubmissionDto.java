package com.assignment.system.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class SubmissionDto {
    private Long id;
    private String content;
    private MultipartFile file;
    private String fileName;
    private String studentName;
    private String assignmentTitle;
    private LocalDateTime submittedAt;
    private Integer score;
    private String feedback;
}

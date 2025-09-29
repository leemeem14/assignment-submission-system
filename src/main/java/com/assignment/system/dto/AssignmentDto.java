package com.assignment.system.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentDto {
    private Long id;

    @NotBlank(message = "과제 제목은 필수입니다")
    private String title;

    private String description;

    @NotNull(message = "마감일은 필수입니다")
    @Future(message = "마감일은 미래 시간이어야 합니다")
    private LocalDateTime dueDate;

    private String professorName;
    private LocalDateTime createdAt;
}

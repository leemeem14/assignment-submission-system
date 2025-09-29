package com.assignment.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;

    @NotBlank(message = "댓글 내용은 필수입니다")
    private String content;

    private String authorName;
    private LocalDateTime createdAt;
}

package com.assignment.system.dto;

import com.assignment.system.entity.Grade;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GradeDto extends Grade {
    private Long id;

    @NotNull(message = "점수는 필수입니다")
    @Min(value = 0, message = "점수는 0 이상이어야 합니다")
    @Max(value = 100, message = "점수는 100 이하여야 합니다")
    private Integer score;

    private String feedback;
    private LocalDateTime gradedAt;
}

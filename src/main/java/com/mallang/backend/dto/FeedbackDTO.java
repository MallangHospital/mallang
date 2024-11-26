package com.mallang.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private Long id;
    private String name;
    private String briefContent; // 내용 요약 (한 줄)
    private LocalDateTime createdDate;
}
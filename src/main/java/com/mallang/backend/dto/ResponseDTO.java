package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Long id;                // 응답 ID
    private Long questionId;        // 질문 ID
    private String questionContent; // 질문 내용
    private Integer answer;         // 응답 값
}
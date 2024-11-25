package com.mallang.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;                // 글 번호 (리뷰 ID)
    private String content;         // 리뷰 내용
    private int star;               // 평점
    private Long doctorId;          // 리뷰 대상 의사 ID
    private String mid;          // 리뷰 작성자 ID
    private double answerRate;      // 답변율(%)
    private double overallRating;   // 전체 평점
    private String consultationMethod; // 상담 경로 (예: 온라인, 오프라인 등)
    private String answer;          // 리뷰에 대한 답변
}
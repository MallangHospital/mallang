package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // 질문과의 관계

    @Column(nullable = false)
    private String memberId; // 응답자의 사용자 ID

    @Column(nullable = false)
    private Integer answer; // 선택한 답변 (1~5 범위: 매우 그렇다 ~ 매우 그렇지 않다)
}
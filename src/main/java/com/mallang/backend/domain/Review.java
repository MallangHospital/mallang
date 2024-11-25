package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 글 번호 (리뷰 ID)

    private String content;          // 리뷰 내용
    private LocalDate writeDate;     // 작성 날짜
    private int star;                // 평점

    private double answerRate;       // 답변율(%)
    private double overallRating;    // 전체 평점

    private String consultationMethod; // 상담 경로 (예: 온라인, 오프라인 등)

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;           // 담당 의사

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;           // 리뷰 작성자

    private String answer;           // 리뷰에 대한 답변

    // @PrePersist를 사용하여 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    // 답변 업데이트 메서드
    public void updateAnswer(String newAnswer) {
        this.answer = newAnswer;
    }

    // 답변율 계산 메서드
    public void calculateAnswerRate(int totalAnswers, int answered) {
        if (totalAnswers != 0) {
            this.answerRate = (answered / (double) totalAnswers) * 100;
        } else {
            this.answerRate = 0.0;
        }
    }

    // 전체 평점 계산 메서드
    public void calculateOverallRating(int totalRatings, int starSum) {
        if (totalRatings != 0) {
            this.overallRating = (double) starSum / totalRatings;
        } else {
            this.overallRating = 0.0;
        }
    }
}
package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; // 사용자 ID
    private Long memberName; // 사용자 이름
    private Long doctorId; // 의사 ID
    private Long doctorName; // 의사 이름
    private Long star; // 전체 별점
    private Long departmentId; // 진료과 ID
    private String departmentName; // 진료과 이름

    @ElementCollection
    private List<Integer> detailStars; // 세분화된 별점 (자세한 설명, 치료후 결과, 직원의 친절, 청결함)

    private String content; // 리뷰 내용
    private String fileUrl; // 첨부 파일 URL
    private Long memberPassword; // 리뷰 삭제 시 비밀번호

    private LocalDateTime createdDate; // 리뷰 등록 날짜

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now(); // 리뷰 등록 날짜 자동 설정
    }
}
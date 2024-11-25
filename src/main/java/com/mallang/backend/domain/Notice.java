package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long으로 수정

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String writer; // 작성자

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private Boolean isSecret; // 비밀글 여부

    private String imagePath; // 대표 이미지 경로

    private String attachmentPath; // 첨부 파일 경로

    @Column(nullable = false)
    private String content; // 본문 내용

    private String link; // 관련 링크

    @Column(nullable = false)
    private String status; // 공개 상태 ("공고" 또는 "비공개")

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 작성 시간

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 작성 시간 자동 설정
        if (status == null) {
            status = isSecret ? "비공개" : "공고"; // 비밀 여부에 따라 상태 설정
        }
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false) // 컬럼 이름은 DB에 맞게 설정, 외래 키 제약조건 추가
    private Admin admin;

}
package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 사용자 ID

    @Column(name = "member_name")
    private String memberName; // 사용자 이름

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId; // 의사 ID

    @Column(name = "doctor_name")
    private String doctorName; // 의사 이름

    @Column(nullable = false)
    private double star; // 전체 별점

    @Column(name = "department_id", nullable = false)
    private Long departmentId; // 진료과 ID

    @Column(name = "department_name")
    private String departmentName; // 진료과 이름

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "review_detail_stars", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "detail_star")
    private List<Integer> detailStars = new ArrayList<>(); // 세분화된 별점

    @Column(nullable = false)
    private String content; // 리뷰 내용

    @Column(name = "file_url")
    private String fileUrl; // 첨부 파일 URL

    @Column(name = "member_password", nullable = false)
    private String memberPassword; // 리뷰 삭제 시 비밀번호

    @Builder.Default
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 리뷰 등록 날짜

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String title; // 건의사항 제목

    @Column(nullable = false, length = 2000)
    private String content; // 건의사항 내용

    @Column(nullable = false)
    private String name; // 고객 이름

    @Column(nullable = false)
    private String phoneNumber; // 고객 휴대폰 번호

    @Column
    private String email; // 고객 이메일 주소 (선택)


    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성 시간

    @ManyToOne
    @JoinColumn(name = "admin_id") // 컬럼 이름은 실제 DB 구조에 맞춰야 함
    private Admin admin; // 관리자로부터 연결된 정보
}
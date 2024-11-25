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
public class OnlineRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String treatmentType; // 진료 종류

    @Column(nullable = false)
    private String department; // 진료 과목

    @Column(nullable = false)
    private String symptoms; // 증상

    @Column(nullable = false)
    private String userName; // 사용자 이름

    @Column(nullable = false)
    private String phone; // 사용자 전화번호

    @Column(nullable = false)
    private LocalDateTime createdAt; // 접수 시간

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
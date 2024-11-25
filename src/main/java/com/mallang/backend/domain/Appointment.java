package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 기본 키

    @ManyToOne(fetch = FetchType.LAZY) // Doctor와의 관계 설정 (다대일)
    @JoinColumn(name = "doctor_id", nullable = false) // 외래키 설정, 반드시 설정
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY) // Department와의 관계 설정 (다대일)
    @JoinColumn(name = "department_id", nullable = false) // 외래키 설정, 반드시 설정
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY) // Member와 다대일 관계 설정
    @JoinColumn(name = "member_id", nullable = false) // Member 외래키 설정
    private Member member; // 환자 정보

    @Column(nullable = false)
    private LocalDate appointmentDate; // 예약 날짜

    @Column(nullable = false)
    private LocalTime appointmentTime; // 예약 시간

    private String symptomDescription; // 증상 설명

}

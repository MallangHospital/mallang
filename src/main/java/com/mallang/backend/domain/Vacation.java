package com.mallang.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate; // 휴진 시작일
    private LocalDate endDate; // 휴진 종료일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // 해당 휴진을 설정하는 의사와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin; // 해당 휴진 정보는 관리자가 설정

    // 기본 생성자
    public Vacation() {}

    // 생성자
    public Vacation(LocalDate startDate, LocalDate endDate, Doctor doctor, Admin admin) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.doctor = doctor;
        this.admin = admin;
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
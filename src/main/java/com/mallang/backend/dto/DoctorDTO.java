package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String name; // 의료진 이름
    private String position; // 전문 분야
    private String phoneNumber; // 전화번호 추가
    private String photoUrl; // 사진 URL 추가
    private LocalDate vacationStartDate; // 휴진 시작일 추가
    private LocalDate vacationEndDate; // 휴진 종료일 추가
    private List<String> history; // 경력 목록
    private String departmentName; // 부서 이름 (Department 엔티티의 이름을 문자열로 저장)
}
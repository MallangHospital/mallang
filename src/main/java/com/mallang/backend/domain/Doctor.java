package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate; // LocalDate로 날짜 처리
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 받는 생성자
@Entity
@Builder

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String name; // 의료진 이름
    private String position; // 전문 분야
    private String phoneNumber; // 연락처
    private String photoUrl; // 의료진 사진 URL
    private LocalDate vacationStartDate; // 휴진 시작일 (LocalDate로 변경)
    private LocalDate vacationEndDate; // 휴진 종료일 (LocalDate로 변경)

    @ElementCollection(fetch = FetchType.EAGER) // 컬렉션을 매핑할 때 사용 (즉시 로딩 설정)
    @CollectionTable(name = "doctor_history", joinColumns = @JoinColumn(name = "doctor_id")) // 별도 테이블로 매핑
    @Column(name = "history_item") // 테이블의 컬럼명 설정
    private List<String> history = new ArrayList<>(); // 경력 목록

    @ManyToOne(fetch = FetchType.LAZY) // optional을 true로 설정할 필요 없음
    @JoinColumn(name = "department_id")
    private Department department; // 의료진이 소속된 부서

    // 기본 생성자와 모든 필드를 초기화하는 생성자가 제공됩니다.
}
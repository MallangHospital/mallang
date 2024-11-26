package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {

    // 관리자 정보
    private String adminId;
    private String AdminName;
    private String AdminPassword;


    // 의료진 정보
    private int doctorId;      // 의료진 ID
    private String doctorName;       // 의료진 이름
    private String doctorSpecialty;  // 의료진 전문 분야
    private String doctorContact;    // 의료진 연락처 (연락처로 수정됨)

    // 휴진 정보
    private int vacationId;    // 휴진 ID
    private String startDate;  // 휴진 시작일
    private String endDate;    // 휴진 종료일

    // 건의사항 정보
    private int feedbackId;     // 건의사항 ID
    private String feedbackTitle;   // 건의사항 제목
    private String feedbackContent; // 건의사항 내용
    private String feedbackStatus;  // 건의사항 상태 ("읽지 않음", "읽음", "처리 중", "완료")

    // 공지사항 정보
    private int noticeId;      // 공지사항 ID
    private String noticeTitle;   // 공지사항 제목
    private String noticeContent; // 공지사항 본문

    // 매거진 정보
    private int magazineId;    // 매거진 ID
    private String magazineTitle;  // 매거진 제목
    private String magazineContent; // 매거진 본문
    private String magazineImagePath; // 매거진 이미지 경로

    // 예약 정보
    private int reservationId;  // 예약 ID
    private String patientName; // 예약 환자 이름
    private String reservationDate; // 예약 일시
    private String patientDoctor; // 담당 의사 이름
    private String reservationStatus; // 예약 상태
}
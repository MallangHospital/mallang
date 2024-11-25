package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.domain.Member;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // 예약 생성
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(
            @AuthenticationPrincipal CustomMemberDetails userDetails, // Spring Security로 인증된 사용자 정보
            @RequestBody AppointmentDTO appointmentDTO) {

        // Spring Security에서 인증된 사용자 정보로 Member 객체 가져오기
        Member member = userDetails.getMember();

        // Service에 Member 객체 전달
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO, member);
        return ResponseEntity.ok(createdAppointment);
    }

    // 특정 회원의 예약 조회
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAppointments(
            @AuthenticationPrincipal CustomMemberDetails userDetails) {
        Member member = userDetails.getMember();
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByMember(member);
        return ResponseEntity.ok(appointments);
    }
}
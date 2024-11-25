package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.service.AppointmentService;
import com.mallang.backend.service.HealthcareReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/my-reservations") // 예약 조회를 위한 명확한 경로 설정
@RequiredArgsConstructor
public class ReservationCheckController {

    private final AppointmentService appointmentService;
    private final HealthcareReserveService healthcareReserveService;

    // 진료 및 건강검진 예약 조회
    @GetMapping
    public ResponseEntity<Map<String, List<?>>> getAllReservations(@AuthenticationPrincipal CustomMemberDetails userDetails) {
        String memberId = userDetails.getUserId();

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByMemberId(memberId);
        List<HealthcareReserveDTO> healthChecks = healthcareReserveService.getHealthReservesByMemberId(memberId);

        Map<String, List<?>> reservations = new HashMap<>();
        reservations.put("appointments", appointments);
        reservations.put("healthChecks", healthChecks);

        return ResponseEntity.ok(reservations);
    }

    // 진료 예약 취소
    @DeleteMapping("/appointment/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment canceled successfully.");
    }

    // 건강검진 예약 취소
    @DeleteMapping("/health-check/{id}")
    public ResponseEntity<?> cancelHealthCheck(@PathVariable Long id) {
        healthcareReserveService.cancelHealthCheck(id);
        return ResponseEntity.ok("Health check reservation canceled successfully.");
    }
}
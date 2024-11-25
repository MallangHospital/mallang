package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.domain.Member;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.service.HealthcareReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthcareReserve")
public class HealthcareReserveController {

    private final HealthcareReserveService healthcareReserveService;

    public HealthcareReserveController(HealthcareReserveService healthcareReserveService) {
        this.healthcareReserveService = healthcareReserveService;
    }

    @PostMapping
    public ResponseEntity<HealthcareReserveDTO> createReservation(
            @AuthenticationPrincipal CustomMemberDetails userDetails, // CustomMemberDetails 주입
            @RequestBody HealthcareReserveDTO dto) {

        // CustomMemberDetails에서 Member 객체를 가져옴
        Member member = userDetails.getMember();
        dto.setMemberId(member.getMid());

        HealthcareReserveDTO createdReservation = healthcareReserveService.createReservation(dto);
        return ResponseEntity.ok(createdReservation);
    }
}
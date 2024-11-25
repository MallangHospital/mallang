package com.mallang.backend.controller;

import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService; // 생성자는 Lombok이 자동 생성

    @GetMapping("/doctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // 특정 부서의 의사 목록 조회
    @GetMapping(params = "departmentId")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartment(@RequestParam Long departmentId) {
        List<DoctorDTO> doctors = doctorService.getDoctorsByDepartment(departmentId);
        return ResponseEntity.ok(doctors);
    }
}

package com.mallang.backend.controller;

import com.mallang.backend.dto.ScheduleDTO;
import com.mallang.backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAvailableSchedules(
            @RequestParam Long doctorId,
            @RequestParam String date) {
        // Convert date from String to LocalDate
        LocalDate parsedDate = LocalDate.parse(date);

        // Fetch available schedules
        List<ScheduleDTO> availableSchedules = scheduleService.getAvailableSchedules(doctorId, parsedDate);

        return ResponseEntity.ok(availableSchedules);
    }
}
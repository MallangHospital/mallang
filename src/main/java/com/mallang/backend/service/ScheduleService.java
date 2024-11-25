package com.mallang.backend.service;

import com.mallang.backend.domain.AvailableTime;
import com.mallang.backend.domain.Schedule;
import com.mallang.backend.dto.ScheduleDTO;
import com.mallang.backend.repository.AvailableTimeRepository;
import com.mallang.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AvailableTimeRepository availableTimeRepository;

    // 예약 가능한 스케줄 조회
    public List<ScheduleDTO> getAvailableSchedules(Long doctorId, LocalDate date) {
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDate(doctorId, date);

        return schedules.stream()
                .map(schedule -> ScheduleDTO.builder()
                        .doctorId(schedule.getDoctor().getId())
                        .date(schedule.getDate())
                        .availableTimes(
                                availableTimeRepository.findByScheduleAndReserved(schedule, false)
                                        .stream()
                                        .map(AvailableTime::getTime)
                                        .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    // 특정 시간 예약 처리
    public void reserveTime(Long scheduleId, LocalTime time) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        // Optional 처리
        AvailableTime availableTime = availableTimeRepository.findByScheduleAndTime(schedule, time)
                .orElseThrow(() -> new IllegalStateException("Time slot is not available"));

        if (availableTime.isReserved()) {
            throw new IllegalStateException("The selected time is already reserved");
        }

        availableTime.setReserved(true); // 예약 처리
        availableTimeRepository.save(availableTime);
    }
}
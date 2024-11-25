package com.mallang.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Long id;
    private Long doctorId;          // 의사 ID

    private LocalDate date;         // 예약 날짜

    private List<LocalTime> availableTimes; // 예약 가능한 시간 목록

}
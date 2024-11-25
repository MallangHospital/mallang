package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Schedule;
import com.mallang.backend.dto.ScheduleDTO;
import com.mallang.backend.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testGetAvailableSchedules() {
        // given
        Long doctorId = 1L;
        LocalDate date = LocalDate.of(2024, 11, 20);

        Doctor doctor = Doctor.builder().id(doctorId).build();

        Schedule schedule1 = Schedule.builder()
                .id(1L)
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .build();

        Schedule schedule2 = Schedule.builder()
                .id(2L)
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(11, 0), LocalTime.of(12, 0)))
                .build();

        when(scheduleRepository.findByDoctorIdAndDate(doctorId, date))
                .thenReturn(Arrays.asList(schedule1, schedule2));

        // when
        List<ScheduleDTO> scheduleDTOs = scheduleService.getAvailableSchedules(doctorId, date);

        // then
        assertNotNull(scheduleDTOs);
        assertEquals(2, scheduleDTOs.size());
        assertEquals(schedule1.getDate(), scheduleDTOs.get(0).getDate());
        assertEquals(schedule1.getAvailableTimes(), scheduleDTOs.get(0).getAvailableTimes());
        assertEquals(schedule2.getDate(), scheduleDTOs.get(1).getDate());
        assertEquals(schedule2.getAvailableTimes(), scheduleDTOs.get(1).getAvailableTimes());

        verify(scheduleRepository, times(1)).findByDoctorIdAndDate(doctorId, date);
    }*/

    @Test
    void testGetAvailableTimes() {
        // given
        Long doctorId = 1L;
        LocalDate date = LocalDate.of(2024, 11, 20);

        Doctor doctor = Doctor.builder().id(doctorId).build();

        Schedule schedule1 = Schedule.builder()
                .id(1L)
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .build();

        Schedule schedule2 = Schedule.builder()
                .id(2L)
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(11, 0), LocalTime.of(12, 0)))
                .build();

        when(scheduleRepository.findByDoctorIdAndDate(doctorId, date))
                .thenReturn(Arrays.asList(schedule1, schedule2));

        // when
        List<LocalTime> availableTimes = scheduleService.getAvailableTimes(doctorId, date);

        // then
        assertNotNull(availableTimes);
        assertEquals(4, availableTimes.size());
        assertTrue(availableTimes.contains(LocalTime.of(9, 0)));
        assertTrue(availableTimes.contains(LocalTime.of(10, 0)));
        assertTrue(availableTimes.contains(LocalTime.of(11, 0)));
        assertTrue(availableTimes.contains(LocalTime.of(12, 0)));

        verify(scheduleRepository, times(1)).findByDoctorIdAndDate(doctorId, date);
    }
}
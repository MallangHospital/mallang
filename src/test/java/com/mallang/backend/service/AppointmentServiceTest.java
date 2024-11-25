package com.mallang.backend.service;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Schedule;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.repository.AppointmentRepository;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment_Success() {
        // given
        Long doctorId = 1L;
        Long departmentId = 2L;
        String memberId = "user123";
        LocalDate date = LocalDate.of(2024, 11, 20);
        LocalTime time = LocalTime.of(10, 0);
        String symptomDescription = "Mild headache";

        Doctor doctor = Doctor.builder().id(doctorId).build();
        Department department = Department.builder().id(departmentId).build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .doctorId(doctorId)
                .departmentId(departmentId)
                .appointmentDate(date)
                .appointmentTime(time)
                .symptomDescription(symptomDescription)
                .build();

        Schedule schedule = Schedule.builder()
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0)))
                .build();

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .department(department)
                .memberId(memberId)
                .appointmentDate(date)
                .appointmentTime(time)
                .symptomDescription(symptomDescription)
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(scheduleRepository.findByDoctorAndDate(doctor, date)).thenReturn(Optional.of(schedule));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // when
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO, memberId);

        // then
        assertNotNull(createdAppointment);
        assertEquals(doctorId, createdAppointment.getDoctorId());
        assertEquals(departmentId, createdAppointment.getDepartmentId());
        assertEquals(date, createdAppointment.getAppointmentDate());
        assertEquals(time, createdAppointment.getAppointmentTime());
        assertEquals(symptomDescription, createdAppointment.getSymptomDescription());

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(scheduleRepository, times(1)).findByDoctorAndDate(doctor, date);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testCreateAppointment_Failure_TimeNotAvailable() {
        // given
        Long doctorId = 1L;
        Long departmentId = 2L;
        String memberId = "user123";
        LocalDate date = LocalDate.of(2024, 11, 20);
        LocalTime time = LocalTime.of(10, 0);

        Doctor doctor = Doctor.builder().id(doctorId).build();
        Department department = Department.builder().id(departmentId).build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .doctorId(doctorId)
                .departmentId(departmentId)
                .appointmentDate(date)
                .appointmentTime(time)
                .build();

        Schedule schedule = Schedule.builder()
                .doctor(doctor)
                .date(date)
                .availableTimes(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(11, 0)))
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(scheduleRepository.findByDoctorAndDate(doctor, date)).thenReturn(Optional.of(schedule));

        // when / then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                appointmentService.createAppointment(appointmentDTO, memberId));

        assertEquals("The selected time is not available for this doctor.", exception.getMessage());

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(scheduleRepository, times(1)).findByDoctorAndDate(doctor, date);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testCreateAppointment_Failure_InvalidDoctorId() {
        // given
        Long doctorId = 1L;
        Long departmentId = 2L;
        String memberId = "user123";
        LocalDate date = LocalDate.of(2024, 11, 20);
        LocalTime time = LocalTime.of(10, 0);

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .doctorId(doctorId)
                .departmentId(departmentId)
                .appointmentDate(date)
                .appointmentTime(time)
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                appointmentService.createAppointment(appointmentDTO, memberId));

        assertEquals("Invalid doctor ID", exception.getMessage());

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(departmentRepository, never()).findById(anyLong());
        verify(scheduleRepository, never()).findByDoctorAndDate(any(), any());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testGetUnavailableTimes() {
        // given
        Long doctorId = 1L;
        LocalDate date = LocalDate.of(2024, 11, 20);

        Doctor doctor = Doctor.builder().id(doctorId).build();

        Appointment appointment1 = Appointment.builder()
                .doctor(doctor)
                .appointmentDate(date)
                .appointmentTime(LocalTime.of(9, 0))
                .build();

        Appointment appointment2 = Appointment.builder()
                .doctor(doctor)
                .appointmentDate(date)
                .appointmentTime(LocalTime.of(10, 0))
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findByDoctorAndAppointmentDate(doctor, date))
                .thenReturn(Arrays.asList(appointment1, appointment2));

        // when
        List<String> unavailableTimes = appointmentService.getUnavailableTimes(doctorId, date);

        // then
        assertNotNull(unavailableTimes);
        assertEquals(2, unavailableTimes.size());
        assertTrue(unavailableTimes.contains("09:00"));
        assertTrue(unavailableTimes.contains("10:00"));

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(appointmentRepository, times(1)).findByDoctorAndAppointmentDate(doctor, date);
    }
}*/
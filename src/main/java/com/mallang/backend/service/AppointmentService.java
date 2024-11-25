package com.mallang.backend.service;

import com.mallang.backend.domain.*;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final AvailableTimeRepository availableTimeRepository;

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Member member) {
        // 의사 정보 검증
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));

        // 부서 정보 검증
        Department department = departmentRepository.findById(appointmentDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // 예약 날짜와 시간의 가용성 확인
        LocalDate date = appointmentDTO.getAppointmentDate();
        LocalTime time = appointmentDTO.getAppointmentTime();

        // 스케줄 확인
        Schedule schedule = scheduleRepository.findByDoctorAndDate(doctor, date)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found for the doctor on the selected date."));

        // AvailableTime 확인 및 예약 가능 여부 체크
        AvailableTime availableTime = availableTimeRepository.findByScheduleAndTime(schedule, time)
                .orElseThrow(() -> new IllegalStateException("The selected time is not available for this doctor."));

        if (availableTime.isReserved()) {
            throw new IllegalStateException("The selected time slot is already reserved.");
        }

        // 예약 생성
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .department(department)
                .member(member) // 인증된 Member 객체 직접 설정
                .appointmentDate(date)
                .appointmentTime(time)
                .symptomDescription(appointmentDTO.getSymptomDescription())
                .build();

        System.out.println("Member during appointment creation: " + member.getName());

        // AvailableTime 예약 상태 업데이트
        availableTime.setReserved(true);
        availableTimeRepository.save(availableTime);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    // 특정 의사의 특정 날짜에 예약 불가 시간 목록 조회
    @Transactional(readOnly = true)
    public List<LocalTime> getUnavailableTimes(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));

        // 예약된 시간 조회
        List<Appointment> appointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, date);
        List<LocalTime> reservedTimes = appointments.stream()
                .map(Appointment::getAppointmentTime)
                .collect(Collectors.toList());

        // 스케줄에서 예약 가능한 시간 확인
        Schedule schedule = scheduleRepository.findByDoctorAndDate(doctor, date)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found for the doctor on the selected date."));

        List<AvailableTime> availableTimes = availableTimeRepository.findBySchedule(schedule);
        List<LocalTime> unavailableTimes = availableTimes.stream()
                .filter(AvailableTime::isReserved)
                .map(AvailableTime::getTime)
                .collect(Collectors.toList());

        // 예약된 시간과 이미 예약된 AvailableTime 합산
        unavailableTimes.addAll(reservedTimes);
        return unavailableTimes.stream().distinct().collect(Collectors.toList());
    }

    // 특정 회원의 예약 조회
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByMember(Member member) {
        List<Appointment> appointments = appointmentRepository.findByMember(member);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByMemberId(String memberId) {
        List<Appointment> appointments = appointmentRepository.findByMember_Mid(memberId);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 취소
    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // 예약된 AvailableTime 상태 초기화
        Schedule schedule = scheduleRepository.findByDoctorAndDate(appointment.getDoctor(), appointment.getAppointmentDate())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        AvailableTime availableTime = availableTimeRepository.findByScheduleAndTime(schedule, appointment.getAppointmentTime())
                .orElseThrow(() -> new IllegalStateException("AvailableTime not found"));

        availableTime.setReserved(false);
        availableTimeRepository.save(availableTime);

        appointmentRepository.deleteById(appointmentId);
    }

    // 모든 진료 예약 조회
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // DTO 생성 메서드
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .departmentId(appointment.getDepartment().getId())
                .patientName(appointment.getMember().getName()) // Member의 이름 가져오기
                .doctorName(appointment.getDoctor().getName()) // Doctor의 이름 가져오기
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .symptomDescription(appointment.getSymptomDescription())
                .build();
    }
}
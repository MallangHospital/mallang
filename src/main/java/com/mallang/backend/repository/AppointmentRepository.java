package com.mallang.backend.repository;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 특정 의사, 날짜, 시간에 이미 예약된 기록이 있는지 확인
    Optional<Appointment> findByDoctorAndAppointmentDateAndAppointmentTime(Doctor doctor, LocalDate date, LocalTime time);

    List<Appointment> findByMember_Mid(String memberId);
    List<Appointment> findByMember(Member member);

    // 특정 의사와 날짜로 예약 목록 조회
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
}
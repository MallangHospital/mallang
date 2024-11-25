package com.mallang.backend.repository;

import com.mallang.backend.domain.Schedule;
import com.mallang.backend.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 특정 의사와 날짜로 스케줄을 조회
    Optional<Schedule> findByDoctorAndDate(Doctor doctor, LocalDate date);

    // 특정 의사 ID와 날짜로 스케줄 목록 조회
    List<Schedule> findByDoctorIdAndDate(Long doctorId, LocalDate date);
}
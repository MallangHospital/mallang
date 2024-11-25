package com.mallang.backend.repository;

import com.mallang.backend.domain.AvailableTime;
import com.mallang.backend.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    List<AvailableTime> findBySchedule(Schedule schedule);

    List<AvailableTime> findByScheduleAndReserved(Schedule schedule, boolean reserved);

    Optional<AvailableTime> findByScheduleAndTime(Schedule schedule, LocalTime time);
}
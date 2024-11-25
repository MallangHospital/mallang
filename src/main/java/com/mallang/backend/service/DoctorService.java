package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class DoctorService {
    private final DoctorRepository doctorRepository;

    // 모든 의사 목록을 가져오는 메서드
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DoctorDTO> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .position(doctor.getPosition())
                .phoneNumber(doctor.getPhoneNumber()) // 전화번호 매핑
                .photoUrl(doctor.getPhotoUrl()) // 사진 URL 매핑
                .vacationStartDate(doctor.getVacationStartDate()) // 휴진 시작일 매핑
                .vacationEndDate(doctor.getVacationEndDate()) // 휴진 종료일 매핑
                .history(doctor.getHistory()) // 경력 매핑
                .departmentName(doctor.getDepartment().getName()) // 부서 이름 매핑
                .build();
    }
}
package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository; // Mock DoctorRepository

    @InjectMocks
    private DoctorService doctorService; // Mock을 주입받는 DoctorService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 초기화
    }

    @Test
    void testGetAllDoctors() {
        // Given: 테스트 데이터 준비
        Doctor doctor1 = new Doctor(
                1L,
                "Dr. Smith",
                "Surgeon",
                "123-456-7890",
                "http://example.com/photo1.jpg",
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 10),
                Arrays.asList("Surgery", "Consulting"),
                null
        );

        Doctor doctor2 = new Doctor(
                2L,
                "Dr. John",
                "Pediatrician",
                "987-654-3210",
                "http://example.com/photo2.jpg",
                LocalDate.of(2024, 12, 15),
                LocalDate.of(2024, 12, 20),
                Arrays.asList("Children Care", "Vaccinations"),
                null
        );

        // Mock 설정
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트 검증
        assertNotNull(doctorDTOList);
        assertEquals(2, doctorDTOList.size());

        // 첫 번째 DoctorDTO 검증
        DoctorDTO doctorDTO1 = doctorDTOList.get(0);
        assertEquals("Dr. Smith", doctorDTO1.getName());
        assertEquals("Surgeon", doctorDTO1.getPosition());
        assertEquals("123-456-7890", doctorDTO1.getPhoneNumber());
        assertEquals("http://example.com/photo1.jpg", doctorDTO1.getPhotoUrl());
        assertEquals("2024-12-01", doctorDTO1.getVacationStartDate());
        assertEquals("2024-12-10", doctorDTO1.getVacationEndDate());
        assertEquals(Arrays.asList("Surgery", "Consulting"), doctorDTO1.getHistory());
        assertNull(doctorDTO1.getDepartmentName());

        // 두 번째 DoctorDTO 검증
        DoctorDTO doctorDTO2 = doctorDTOList.get(1);
        assertEquals("Dr. John", doctorDTO2.getName());
        assertEquals("Pediatrician", doctorDTO2.getPosition());
        assertEquals("987-654-3210", doctorDTO2.getPhoneNumber());
        assertEquals("http://example.com/photo2.jpg", doctorDTO2.getPhotoUrl());
        assertEquals("2024-12-15", doctorDTO2.getVacationStartDate());
        assertEquals("2024-12-20", doctorDTO2.getVacationEndDate());
        assertEquals(Arrays.asList("Children Care", "Vaccinations"), doctorDTO2.getHistory());
        assertNull(doctorDTO2.getDepartmentName());

        // Verify: findAll() 호출 확인
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDoctors_emptyList() {
        // Given: 빈 리스트 반환 설정
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트 검증
        assertNotNull(doctorDTOList);
        assertTrue(doctorDTOList.isEmpty());

        // Verify: findAll() 호출 확인
        verify(doctorRepository, times(1)).findAll();
    }
}
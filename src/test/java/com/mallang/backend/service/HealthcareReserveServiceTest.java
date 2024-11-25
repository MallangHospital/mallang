package com.mallang.backend.service;

import com.mallang.backend.domain.HealthcareReserve;
import com.mallang.backend.domain.HealthcareType;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.repository.HealthcareReserveRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HealthcareReserveServiceTest {

    @Autowired
    private HealthcareReserveService healthcareReserveService;

    @MockBean
    private HealthcareReserveRepository healthcareReserveRepository;

    @Test
    void createReservation() {
        // given: 테스트용 DTO 생성
        HealthcareReserveDTO dto = HealthcareReserveDTO.builder()
                .name("John Doe")
                .memberId("user123")
                .phoneNumber("010-1234-5678")
                .reserveDate(LocalDate.now())
                .hType(HealthcareType.개인_건강검진)
                .build();

        // when: 저장될 엔티티 Mock 설정
        HealthcareReserve savedReservation = HealthcareReserve.builder()
                .hId(1L)
                .name(dto.getName())
                .memberId(dto.getMemberId())
                .phoneNumber(dto.getPhoneNumber())
                .reserveDate(dto.getReserveDate())
                .hType(dto.getHType())
                .build();

        when(healthcareReserveRepository.save(any(HealthcareReserve.class))).thenReturn(savedReservation);

        // 실행: 서비스 메서드 호출
        HealthcareReserveDTO result = healthcareReserveService.createReservation(dto);

        // then: 결과 검증
        assertNotNull(result);
        assertEquals(savedReservation.getHId(), result.getHId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getMemberId(), result.getMemberId());
        assertEquals(dto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(dto.getReserveDate(), result.getReserveDate());
        assertEquals(dto.getHType(), result.getHType());

        // verify: Repository의 save가 한 번 호출되었는지 확인
        Mockito.verify(healthcareReserveRepository, Mockito.times(1)).save(any(HealthcareReserve.class));
    }
}
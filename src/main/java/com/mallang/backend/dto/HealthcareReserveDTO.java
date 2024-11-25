package com.mallang.backend.dto;

import com.mallang.backend.domain.HealthcareType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthcareReserveDTO {
    private Long hId;
    private String name;
    private String memberId;
    private String phoneNumber;
    private LocalDate reserveDate;
    private HealthcareType hType;
}
package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberLoginDTO {
    private String mid;
    private String mpw;
}

package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberJoinDTO {
    private String mid;
    private String mpw;
    private String email;
    private String name;
    private String phoneNum; //휴대폰번호
    private String rrn; //주민등록번호
}

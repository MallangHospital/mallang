package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMemberDTO {
    private String mid; // 사용자 ID
    private String mpw; // 비밀번호 (필요시)
    private String email; // 이메일
    private String name; // 이름
    private String phoneNum; // 전화번호
    private String rrn; // 주민등록번호
    private String role; // 역할
    private boolean agreeToTerms; // 약관 동의 여부
}
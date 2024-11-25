package com.mallang.backend.dto;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String mid;        // 회원 ID
    private String name;       // 이름
    private String email;      // 이메일
    private String phoneNum;   // 전화번호
    private String rrn;        // 주민등록번호
}
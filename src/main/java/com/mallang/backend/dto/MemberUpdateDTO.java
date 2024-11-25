package com.mallang.backend.dto;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDTO {
    private String currentPassword; // 현재 비밀번호 확인용
    private String newPassword;     // 새 비밀번호
    private String phoneNum;        // 새 전화번호
    private String email;      // 이메일
}
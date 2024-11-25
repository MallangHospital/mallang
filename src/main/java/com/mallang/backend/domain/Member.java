package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {
    @Id
    @Column(length = 50, nullable = false)
    private String mid;

    @Column(length = 255, nullable = false)
    private String mpw;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255, nullable = false, unique = true)
    private String phoneNum;

    @Column(length = 255, nullable = false)
    private String rrn; // 주민등록번호

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean agreeToTerms;

    public void changePassword(String mpw) {
        this.mpw = mpw;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void setMid(String mid) {this.mid = mid;}

    // 전화번호 변경
    public void changePhoneNumber(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
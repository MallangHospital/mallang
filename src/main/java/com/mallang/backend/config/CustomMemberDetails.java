package com.mallang.backend.config;

import com.mallang.backend.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomMemberDetails implements UserDetails {
    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name())); // 역할을 SimpleGrantedAuthority로 설정
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getMpw();
    }

    public String getUserId() {
        return member.getMid();
    }

    @Override
    public String getUsername() {
        return member.getMid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Member 객체를 반환하는 메서드 추가
    public Member getMember() {
        return member;
    }
}
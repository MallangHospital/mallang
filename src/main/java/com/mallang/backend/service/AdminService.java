package com.mallang.backend.service;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.dto.AdminMemberDTO;
import com.mallang.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 가입 처리
    /*public Member registerMember(AdminMemberDTO memberDTO) {
        Member member = new Member();
        member.setMid(memberDTO.getMid());
        member.setMpw(memberDTO.getMpw());
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setPhoneNum(memberDTO.getPhoneNum()); // 이제 사용 가능
        member.setRrn(memberDTO.getRrn());
        member.setRole(Role.valueOf(memberDTO.getRole())); // Role enum으로 변환
        member.setAgreeToTerms(memberDTO.isAgreeToTerms());

        return memberRepository.save(member);
    }*/

    // 나머지 메서드는 동일
}
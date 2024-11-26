package com.mallang.backend.service;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.dto.MemberDTO;
import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.dto.MemberUpdateDTO;
import com.mallang.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberJoinDTO memberJoinDTO) {

        // 아이디 중복 확인
        if (memberRepository.existsById(memberJoinDTO.getMid())) {
            throw new IllegalArgumentException("아이디가 이미 존재합니다.");
        }

        // 비밀번호 형식 검사
        if (!isValidPassword(memberJoinDTO.getMpw())) {
            throw new IllegalArgumentException("비밀번호가 형식에 맞지 않습니다.");
        }

        // 전화번호 형식 검사
        if (!isValidPhoneNumber(memberJoinDTO.getPhoneNum())) {
            throw new IllegalArgumentException("전화번호가 형식에 맞지 않습니다.");
        }

        // 이메일 형식 검사
        if (!isValidEmail(memberJoinDTO.getEmail())) {
            throw new IllegalArgumentException("이메일이 형식에 맞지 않습니다.");
        }

        // 모든 필수 항목 입력 여부 확인
        if (memberJoinDTO.getMid() == null || memberJoinDTO.getMpw() == null || memberJoinDTO.getName() == null ||
                memberJoinDTO.getEmail() == null || memberJoinDTO.getPhoneNum() == null) {
            throw new IllegalArgumentException("모든 항목이 입력되지 않았습니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberJoinDTO.getMpw());
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(encodedPassword);

        // 아이디가 'admin'으로 시작하면 ROLE_ADMIN, 그렇지 않으면 ROLE_MEMBER 설정
        if (member.getMid().startsWith("admin")) {
            member.changeRole(Role.ROLE_ADMIN);  // 관리자 권한 설정
        } else {
            member.changeRole(Role.ROLE_MEMBER);  // 기본 회원 권한 설정
        }

        memberRepository.save(member);
    }

    // 비밀번호 형식 검사 로직
    private boolean isValidPassword(String password) {
        // 비밀번호 형식 조건: 최소 8글자
        return password.length() >= 8;
    }
    // 전화번호 형식 검사 로직
    private boolean isValidPhoneNumber(String phoneNum) {
        // 전화번호 형식: 010-1234-5678 형태
        return phoneNum.matches("^010-\\d{4}-\\d{4}$");
    }

    // 이메일 형식 검사 로직
    private boolean isValidEmail(String email) {
        // 이메일 형식 확인: 대략적인 형식 검사
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // 아이디 중복 검사 메서드
    public boolean isDuplicateId(String mid) {
        // 해당 아이디가 이미 존재하는지 확인
        return memberRepository.existsById(mid);
    }

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public MemberDTO getMemberInfo(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberDTO.builder()
                .mid(member.getMid())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNum(member.getPhoneNum())
                .rrn(member.getRrn())
                .build();
    }

    public void updateMember(String memberId, MemberUpdateDTO updateDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 비밀번호 변경
        if (updateDTO.getCurrentPassword() != null && updateDTO.getNewPassword() != null) {
            if (!passwordEncoder.matches(updateDTO.getCurrentPassword(), member.getMpw())) {
                throw new IllegalArgumentException("Invalid current password");
            }
            if (!isValidPassword(updateDTO.getNewPassword())) {
                throw new IllegalArgumentException("New password does not meet the requirements.");
            }
            member.changePassword(passwordEncoder.encode(updateDTO.getNewPassword()));
        }

        // 이메일 변경
        if (updateDTO.getEmail() != null) {
            if (!isValidEmail(updateDTO.getEmail())) {
                throw new IllegalArgumentException("Invalid email format.");
            }
            member.changeEmail(updateDTO.getEmail());
        }

        // 전화번호 변경
        if (updateDTO.getPhoneNum() != null) {
            if (!isValidPhoneNumber(updateDTO.getPhoneNum())) {
                throw new IllegalArgumentException("Invalid phone number format.");
            }
            member.changePhoneNumber(updateDTO.getPhoneNum());
        }

        memberRepository.save(member);
    }

    /*public boolean verifyPassword(String memberId, String rawPassword) {
        // DB에서 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Encoded Password: " + member.getMpw());

        System.out.println("Password matches: " + passwordEncoder.matches("password1234!", "$2a$10$cFFlGYu83rK52z7xgjHAsOSIrn1rooheb/yErfuXOAAD35qy0ihze"));
        // 암호화된 비밀번호와 입력한 비밀번호 비교
        return passwordEncoder.matches(rawPassword, member.getMpw());
    }*/
}
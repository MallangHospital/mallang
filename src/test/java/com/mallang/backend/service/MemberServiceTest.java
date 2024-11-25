package com.mallang.backend.service;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ModelMapper modelMapper;

    private MemberJoinDTO memberJoinDTO;

    @BeforeEach
    void setUp() {
        memberJoinDTO = MemberJoinDTO.builder()
                .mid("testUser")
                .mpw("password123")
                .email("test@example.com")
                .name("Test User")
                .phoneNum("010-1234-5678")
                .rrn("0202022012432")
                .agreeToTerms(true)
                .build();
    }

    @Test
    void testJoin_Success() {
        // given
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(false);  // 아이디 중복 검사 모킹
        when(passwordEncoder.encode(memberJoinDTO.getMpw())).thenReturn("encodedPassword");  // 비밀번호 암호화 모킹

        Member member = new Member();
        member.setMid(memberJoinDTO.getMid());
        when(modelMapper.map(any(MemberJoinDTO.class), eq(Member.class))).thenReturn(member);

        // when
        memberService.join(memberJoinDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));  // 회원 정보가 저장되었는지 확인
    }

    @Test
    void testJoin_Fail_TermsNotAgreed() {
        // given
        memberJoinDTO.setAgreeToTerms(false);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("약관에 동의해야 합니다.", exception.getMessage());
    }

    @Test
    void testJoin_Fail_DuplicateId() {
        // given
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(true);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("아이디가 이미 존재합니다.", exception.getMessage());
    }

    @Test
    void testJoin_Fail_InvalidPassword() {
        // given
        memberJoinDTO.setMpw("short");  // 짧은 비밀번호

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("비밀번호가 형식에 맞지 않습니다.", exception.getMessage());
    }

    @Test
    void testJoin_Fail_InvalidPhoneNumber() {
        // given
        memberJoinDTO.setPhoneNum("012-1234-5678");  // 잘못된 전화번호 형식

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("전화번호가 형식에 맞지 않습니다.", exception.getMessage());
    }

    @Test
    void testJoin_Fail_InvalidEmail() {
        // given
        memberJoinDTO.setEmail("invalid-email");  // 잘못된 이메일 형식

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("이메일이 형식에 맞지 않습니다.", exception.getMessage());
    }

    @Test
    void testJoin_Fail_MissingFields() {
        // given
        memberJoinDTO.setMid(null);  // 아이디가 누락된 상태

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.join(memberJoinDTO));

        // then
        assertEquals("모든 항목이 입력되지 않았습니다.", exception.getMessage());
    }

    @Test
    void testJoin_RoleAssigned_Admin() {
        // given
        memberJoinDTO.setMid("adminUser");  // 아이디가 'admin'으로 시작
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(false);
        when(passwordEncoder.encode(memberJoinDTO.getMpw())).thenReturn("encodedPassword");

        Member member = new Member();
        member.setMid(memberJoinDTO.getMid());
        when(modelMapper.map(any(MemberJoinDTO.class), eq(Member.class))).thenReturn(member);

        // when
        memberService.join(memberJoinDTO);

        // then
        assertEquals(Role.ROLE_ADMIN, member.getRole());  // ROLE_ADMIN이 설정되었는지 확인
    }

    @Test
    void testJoin_RoleAssigned_Member() {
        // given
        memberJoinDTO.setMid("regularUser");  // 일반 아이디
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(false);
        when(passwordEncoder.encode(memberJoinDTO.getMpw())).thenReturn("encodedPassword");

        Member member = new Member();
        member.setMid(memberJoinDTO.getMid());
        when(modelMapper.map(any(MemberJoinDTO.class), eq(Member.class))).thenReturn(member);

        // when
        memberService.join(memberJoinDTO);

        // then
        assertEquals(Role.ROLE_MEMBER, member.getRole());  // ROLE_MEMBER가 설정되었는지 확인
    }

    @Test
    void testIsDuplicateId_True() {
        // given
        when(memberRepository.existsById("testUser")).thenReturn(true);

        // when
        boolean result = memberService.isDuplicateId("testUser");

        // then
        assertTrue(result);
    }

    @Test
    void testIsDuplicateId_False() {
        // given
        when(memberRepository.existsById("newUser")).thenReturn(false);

        // when
        boolean result = memberService.isDuplicateId("newUser");

        // then
        assertFalse(result);
    }
}
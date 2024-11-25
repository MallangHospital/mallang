package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.MemberDTO;
import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.dto.MemberUpdateDTO;
import com.mallang.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    // 회원가입 요청 처리
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDTO memberJoinDTO) {
        try {
            memberService.join(memberJoinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 아이디 중복 확인
    @GetMapping("/check-id/{mid}")
    public ResponseEntity<String> checkId(@PathVariable String mid) {
        if (memberService.isDuplicateId(mid)) {
            return ResponseEntity.badRequest().body("아이디가 중복되었습니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }

    // 회원 정보 조회
    @GetMapping("/info")
    public ResponseEntity<MemberDTO> getMemberInfo(@AuthenticationPrincipal CustomMemberDetails userDetails) {
        String memberId = userDetails.getUserId();
        MemberDTO memberDTO = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberDTO);
    }

    // 회원 정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateMember(
            @AuthenticationPrincipal CustomMemberDetails userDetails,
            @RequestBody MemberUpdateDTO memberUpdateDTO) {

        String memberId = userDetails.getUserId(); // 인증된 사용자 ID 가져오기
        memberService.updateMember(memberId, memberUpdateDTO);
        return ResponseEntity.ok("Member information updated successfully.");
    }

    /*// 비밀번호 확인
    @PostMapping("/verify-password")
    public ResponseEntity<?> verifyPassword(
            @AuthenticationPrincipal CustomMemberDetails userDetails,
            @RequestBody String password) {

        String memberId = userDetails.getUserId();
        boolean isPasswordValid = memberService.verifyPassword(memberId, password);

        if (!isPasswordValid) {
            return ResponseEntity.status(401).body("Invalid password.");
        }
        return ResponseEntity.ok("Password verified successfully.");
    }*/
}
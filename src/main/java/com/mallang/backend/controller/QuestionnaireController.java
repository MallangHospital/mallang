package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.QuestionDTO;
import com.mallang.backend.dto.ResponseDTO;
import com.mallang.backend.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    // 문진표 질문 조회
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionnaireService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // 문진표 응답 저장
    @PostMapping("/responses")
    public ResponseEntity<?> saveResponses(
            @AuthenticationPrincipal CustomMemberDetails userDetails,
            @RequestBody List<ResponseDTO> responses) {

        // CustomMemberDetails에서 memberId 가져오기
        String memberId = userDetails.getUserId();
        if (memberId == null || memberId.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid member ID");
        }

        // 서비스 호출
        questionnaireService.saveResponses(memberId, responses);
        return ResponseEntity.ok("Responses saved successfully!");
    }

    // 특정 사용자의 문진표 응답 조회
    @GetMapping("/responses")
    public ResponseEntity<List<ResponseDTO>> getResponsesByMemberId(
            @AuthenticationPrincipal CustomMemberDetails userDetails) {

        // CustomMemberDetails에서 memberId 가져오기
        String memberId = userDetails.getUserId();
        if (memberId == null || memberId.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<ResponseDTO> responses = questionnaireService.getResponses(memberId);
        return ResponseEntity.ok(responses);
    }
}
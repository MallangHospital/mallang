package com.mallang.backend.controller;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // 건의사항 접수
    @PostMapping
    public ResponseEntity<String> submitFeedback(@RequestBody Feedback feedback) {
        if (feedback.getTitle() == null || feedback.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다. 다시 확인해 주세요.");
        }
        if (feedback.getContent() == null || feedback.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("내용란이 비어 있습니다. 내용을 입력해 주세요.");
        }
        if (feedback.getName() == null || feedback.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("이름이 입력되지 않았습니다. 다시 확인해 주세요.");
        }
        if (feedback.getPhoneNumber() == null || feedback.getPhoneNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("휴대폰 번호가 입력되지 않았습니다. 다시 확인해 주세요.");
        }

        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return ResponseEntity.ok("건의사항이 접수되었습니다. ID: " + savedFeedback.getId());
    }
}
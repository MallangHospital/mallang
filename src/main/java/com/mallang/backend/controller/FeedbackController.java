package com.mallang.backend.controller;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public String submitFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return "건의사항이 접수되었습니다. ID: " + savedFeedback.getId();
    }
}
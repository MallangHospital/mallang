package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // 건의사항 등록
    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // 모든 건의사항 조회
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // 건의사항 상태 변경
    public Feedback updateFeedbackStatus(Long id, String status) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("건의사항 ID가 존재하지 않습니다."));
        feedback.setStatus(status); // 상태 업데이트
        return feedbackRepository.save(feedback);
    }
}
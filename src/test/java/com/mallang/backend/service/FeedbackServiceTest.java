package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
@SpringBootTest
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository; // mock FeedbackRepository

    @InjectMocks
    private FeedbackService feedbackService; // FeedbackService에 mock된 FeedbackRepository를 주입

    @Test
    void testSubmitFeedback() {
        // Given: 테스트 데이터 준비
        Feedback feedback = new Feedback();
        feedback.setTitle("Great service");
        feedback.setContent("The service was excellent.");
        feedback.setName("John Doe");
        feedback.setPhoneNumber("123-456-7890");
        feedback.setEmail("johndoe@example.com");

        // mock FeedbackRepository가 save 메서드를 호출할 때 위 피드백 객체를 반환하도록 설정
        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // When: 서비스 메서드 호출
        Feedback savedFeedback = feedbackService.submitFeedback(feedback);

        // Then: 반환된 피드백 객체 검증
        assertNotNull(savedFeedback); // 반환값이 null이 아님을 확인
        assertEquals("Great service", savedFeedback.getTitle()); // 제목 검증
        assertEquals("The service was excellent.", savedFeedback.getContent()); // 내용 검증
        assertEquals("John Doe", savedFeedback.getName()); // 이름 검증

        // Verify: mock 메서드가 호출되었는지 확인
        verify(feedbackRepository, times(1)).save(feedback); // save() 메서드가 정확히 한 번 호출되었는지 확인
    }
}
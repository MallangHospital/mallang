package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository; // ReviewRepository를 mock 처리

    @InjectMocks
    private ReviewService reviewService; // ReviewService에 mock된 ReviewRepository를 주입

    @Test
    void testGetAllReviews() {
        // Given: 테스트 데이터 준비
        Doctor doctor = mock(Doctor.class);
        when(doctor.getId()).thenReturn(1L);  // Mock에서 id 반환 설정

        Member member = new Member();
        member.setMid("100");  // 회원 ID 설정

        Review review1 = new Review();
        review1.setId(1L);
        review1.setContent("Great doctor!");
        review1.setStar(5);
        review1.setDoctor(doctor);
        review1.setMember(member);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setContent("Not bad");
        review2.setStar(3);
        review2.setDoctor(doctor);
        review2.setMember(member);

        // mock ReviewRepository가 findAll을 호출할 때 위 리뷰들을 반환하도록 설정
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        // When: 서비스 메서드 호출
        List<ReviewDTO> reviewDTOList = reviewService.getAllReviews();

        // Then: 반환된 리뷰 DTO 리스트 검증
        assertNotNull(reviewDTOList); // 리스트가 null이 아님을 확인
        assertEquals(2, reviewDTOList.size()); // 두 개의 리뷰가 반환되어야 함

        // 첫 번째 ReviewDTO 검증
        ReviewDTO reviewDTO1 = reviewDTOList.get(0);
        assertEquals(1L, reviewDTO1.getId()); // ID 검증
        assertEquals("Great doctor!", reviewDTO1.getContent()); // 내용 검증
        assertEquals(5, reviewDTO1.getStar()); // 평점 검증
        assertEquals(1L, reviewDTO1.getDoctorId()); // 의사 ID 검증
        assertEquals("100", reviewDTO1.getMid()); // 회원 ID 검증

        // 두 번째 ReviewDTO 검증
        ReviewDTO reviewDTO2 = reviewDTOList.get(1);
        assertEquals(2L, reviewDTO2.getId()); // ID 검증
        assertEquals("Not bad", reviewDTO2.getContent()); // 내용 검증
        assertEquals(3, reviewDTO2.getStar()); // 평점 검증
        assertEquals(1L, reviewDTO2.getDoctorId()); // 의사 ID 검증
        assertEquals("100", reviewDTO2.getMid()); // 회원 ID 검증

        // Verify: mock 메서드 호출 확인
        verify(reviewRepository, times(1)).findAll(); // findAll()이 한 번 호출되었는지 확인
    }

    @Test
    void testCreateReview() {
        // Given: 테스트 데이터 준비
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(0L)
                .content("Excellent service")
                .star(5)
                .doctorId(1L)
                .mid("100")
                .build();

        Doctor doctor = mock(Doctor.class);
        when(doctor.getId()).thenReturn(1L);  // Mock에서 id 반환 설정

        Member member = new Member();
        member.setMid("100");  // 회원 ID 설정

        // Review 엔티티 생성
        Review savedReview = new Review();
        savedReview.setId(1L);
        savedReview.setContent("Excellent service");
        savedReview.setStar(5);
        savedReview.setDoctor(doctor);
        savedReview.setMember(member);

        // mock ReviewRepository가 save 메서드를 호출할 때 저장된 리뷰를 반환하도록 설정
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When: 서비스 메서드 호출
        ReviewDTO createdReviewDTO = reviewService.createReview(reviewDTO, doctor, member);

        // Then: 반환된 ReviewDTO 검증
        assertNotNull(createdReviewDTO); // 반환값이 null이 아님을 확인
        assertEquals(1L, createdReviewDTO.getId()); // ID 검증
        assertEquals("Excellent service", createdReviewDTO.getContent()); // 내용 검증
        assertEquals(5, createdReviewDTO.getStar()); // 평점 검증
        assertEquals(1L, createdReviewDTO.getDoctorId()); // 의사 ID 검증
        assertEquals("100", createdReviewDTO.getMid()); // 회원 ID 검증

        // Verify: mock 메서드 호출 확인
        verify(reviewRepository, times(1)).save(any(Review.class)); // save()가 한 번 호출되었는지 확인
    }
}
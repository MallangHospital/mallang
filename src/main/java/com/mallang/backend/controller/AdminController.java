package com.mallang.backend.controller;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.FeedbackDTO;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.AdminService;
import com.mallang.backend.service.HealthcareReserveService;
import com.mallang.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final HealthcareReserveService healthcareReserveService;

    @Autowired
    private AdminService adminService;
    private final ReviewService reviewService;

    // HealthcareReserveService를 AdminController에 주입
    public AdminController(HealthcareReserveService healthcareReserveService, ReviewService reviewService) {
        this.healthcareReserveService = healthcareReserveService;
        this.reviewService = reviewService;
    }

    // 관리자 등록
    @PostMapping("/register")
    public String registerAdmin(@RequestParam String adminId, @RequestParam String adminPassword) {
        try {
            adminService.registerAdmin(adminId, adminPassword);
            return "관리자 계정이 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 관리자 인증
    @PostMapping("/login")
    public String authenticateAdmin(@RequestParam String adminId, @RequestParam String adminPassword) {
        try {
            adminService.authenticateAdmin(adminId, adminPassword);
            return "로그인 성공.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 관리자 삭제
    @DeleteMapping("/{adminName}")
    public String deleteAdmin(@PathVariable String adminName) {
        try {
            adminService.deleteAdmin(adminName);
            return "관리자 계정이 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 관리자 정보 조회
    @GetMapping("/{adminId}")
    public String getAdminById(@PathVariable String adminId) {
        try {
            return adminService.getAdminById(adminId).toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 관리자 정보 수정
    @PutMapping("/{adminId}")
    public String updateAdmin(
            @PathVariable String adminId,
            @RequestParam(required = false) String newId,
            @RequestParam(required = false) String newPassword) {
        try {
            adminService.updateAdmin(adminId, newId, newPassword);
            return "관리자 정보가 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 등록
    @PostMapping("/doctors")
    public String registerDoctor(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String specialty,
                                 @RequestParam(required = false) String contact) {
        // 입력값 검증
        if (name == null || name.trim().isEmpty() || specialty == null || specialty.trim().isEmpty() || contact == null || contact.trim().isEmpty()) {
            return "의료진 정보가 입력되지 않았습니다. 확인해주세요.";
        }

        // 유효성 검증 통과 시, 등록 처리
        try {
            adminService.registerDoctor(name, specialty, contact);
            return "의료진이 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 수정
    @PutMapping("/doctors/{doctorId}")
    public String updateDoctor(@PathVariable int doctorId, @RequestParam String name, @RequestParam String specialty, @RequestParam String contact) {
        try {
            adminService.updateDoctor(doctorId, name, specialty, contact);
            return "의료진 정보가 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 삭제
    @DeleteMapping("/doctors/{doctorId}")
    public String deleteDoctor(@PathVariable int doctorId) {
        adminService.deleteDoctor(doctorId);
        return "삭제되었습니다.";
    }

    // 의료진 휴진 정보 등록
    @PostMapping("/vacations")
    public String registerVacation(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate) {
        // 각 파라미터 입력값 검증
        if (name == null || name.trim().isEmpty()) {
            return "의료진 이름이 입력되지 않았습니다.";
        }
        if (startDate == null || startDate.trim().isEmpty()) {
            return "휴진 시작일이 입력되지 않았습니다.";
        }
        if (endDate == null || endDate.trim().isEmpty()) {
            return "휴진 종료일이 입력되지 않았습니다.";
        }

        // 유효성 검증 통과 시, 휴진 정보 등록 처리
        try {
            adminService.registerVacation(name, startDate, endDate);
            return "휴진정보 등록이 완료되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 휴진 정보 수정
    @PutMapping("/doctors/{doctorId}/vacation")
    public String updateDoctorVacation(
            @PathVariable int doctorId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            adminService.updateVacation(doctorId, startDate, endDate);
            return "휴진정보가 변경되었습니다..";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return "날짜 형식이 잘못되었습니다. (yyyy-MM-dd)";
        }
    }

    // 의료진 휴진 정보 삭제
    @DeleteMapping("/vacations/{vacationId}")
    public String deleteVacation(@PathVariable int vacationId) {
        adminService.deleteVacation(vacationId);
        return "휴진 정보가 삭제되었습니다.";
    }

    // 건의사항 목록 조회
    @GetMapping("/feedbacks")
    public List<Feedback> getFeedbackList() {
        return adminService.getFeedbackList();
    }

    // 건의사항 상태 변경 (읽음으로 설정)
    @PutMapping("/feedbacks/{id}/read")
    public String markFeedbackAsRead(@PathVariable Long id) {
        try {
            adminService.markFeedbackAsRead(id);
            return "건의사항이 읽음 상태로 변경되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 공지사항 등록
    @PostMapping("/notices")
    public ResponseEntity<String> registerNotice(
            @RequestParam String title,
            @RequestParam String writer,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Boolean isSecret, // 비밀글 여부
            @RequestParam MultipartFile representativeImage, // 대표 이미지
            @RequestParam MultipartFile attachment, // 첨부 파일
            @RequestParam String content,
            @RequestParam String link,
            @RequestParam String captcha // 자동입력 방지
    ) {
        try {
            // 비밀번호 및 필수 입력값 검증
            validateNoticeInput(title, content, password, captcha);

            // 공지사항 등록 서비스 호출
            adminService.registerNotice(
                    title, writer, email, password, isSecret, representativeImage, attachment, content, link);

            return ResponseEntity.ok("공지사항이 성공적으로 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("등록 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("시스템 오류: " + e.getMessage());
        }
    }

    // 공지사항 목록 조회
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getAllNotices() {
        try {
            List<Notice> notices = adminService.getAllNotices();
            return ResponseEntity.ok(notices);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/notices")
    public ResponseEntity<String> deleteNotices(@RequestParam List<Long> noticeIds) {
        try {
            adminService.deleteNotices(noticeIds);
            return ResponseEntity.ok("선택한 공지사항이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 중 오류 발생: " + e.getMessage());
        }
    }

    //입력 검증 메서드
    private void validateNoticeInput(String title, String content, String password, String captcha) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목이 입력되지 않았습니다. 다시 확인해 주세요");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("본문란이 비어 있습니다. 내용을 입력해 주세요");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요");
        }
        if (captcha == null || !captcha.equals("12345")) { // 간단한 CAPTCHA 검증 예제
            throw new IllegalArgumentException("자동입력 방지 코드가 틀렸습니다.");
        }
    }

    // 매거진 등록
    @PostMapping("/magazines")
    public String registerMagazine(@RequestParam String title, @RequestParam String content, @RequestParam String password) {
        try {
            validatePassword(password);
            adminService.registerMagazine(title, content);
            return "건강 매거진이 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return "등록 실패: " + e.getMessage();
        }
    }

    // 매거진 수정
    @PutMapping("/magazines/{magazineId}")
    public String updateMagazine(@PathVariable int magazineId, @RequestParam String title, @RequestParam String content) {
        try {
            adminService.updateMagazine(magazineId, title, content);
            return "건강 매거진이 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return "수정 실패: " + e.getMessage();
        }
    }


    // 매거진 삭제
    @DeleteMapping("/magazines/{magazineId}")
    public String deleteMagazine(@PathVariable int magazineId) {
        adminService.deleteMagazine(magazineId);
        return "건강 매거진이 삭제되었습니다.";
    }


    // 모든 건강검진 예약 조회
    @GetMapping("/reserves")
    public List<HealthcareReserveDTO> getAllHealthReserves() {
        return adminService.getAllReservations();
    }



    // 모든 리뷰 조회
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // 특정 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReviewById(id);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 삭제 중 오류가 발생했습니다.");
        }
    }

    // 전체 리뷰 통계 조회 (전체적인 별점 평균, 세분화된 별점 평균, 총 리뷰 수)
    @GetMapping("/reviews/statistics")
    public ResponseEntity<?> getReviewStatistics() {
        try {
            var statistics = reviewService.calculateReviewStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("통계 조회 중 오류가 발생했습니다.");
        }
    }


    // 비밀번호 유효성 검사
    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해야 합니다.");
        }
    }
}
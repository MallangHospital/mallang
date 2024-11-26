package com.mallang.backend.service;

import com.mallang.backend.domain.*;

import com.mallang.backend.dto.AdminDTO;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final HealthcareReserveService healthcareReserveService;
    private final AppointmentRepository appointmentRepository; // OnlineRegistrationService 주입
    private final ReviewRepository reviewRepository;
    private final NoticeRepository noticeRepository;
    private final FeedbackRepository feedbackRepository;;
    private final OnlineRegistrationRepository onlineRegistrationRepository;


    // 생성자를 통해 의존성 주입
    public AdminService(AdminRepository adminRepository, HealthcareReserveService healthcareReserveService,
                        AppointmentRepository appointmentRepository, ReviewRepository reviewRepository,
                        NoticeRepository noticeRepository, FeedbackRepository feedbackRepository, OnlineRegistrationRepository onlineRegistrationRepository) {
        this.adminRepository = adminRepository;
        this.healthcareReserveService = healthcareReserveService;
        this.appointmentRepository = appointmentRepository;
        this.reviewRepository = reviewRepository;

        this.noticeRepository = noticeRepository;
        this.feedbackRepository = feedbackRepository;
        this.onlineRegistrationRepository = onlineRegistrationRepository;
    }

    // 관리자 등록
    public void registerAdmin(String Id, String password) {
        if (Id == null || Id.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("관리자 계정의 아이디와 비밀번호를 입력해야 합니다.");
        }
        if (adminRepository.findByAdminId(Id).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        Admin admin = new Admin();
        admin.setAdminId(Id);
        admin.setPassword(password);

        adminRepository.save(admin);
        System.out.println("관리자 등록 완료: "+ admin.getAdminName());
    }

    // 관리자 인증
    public void authenticateAdmin(String Id, String password) {
        adminRepository.findByAdminIdAndPassword(Id, password)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));
    }

    // 관리자 삭제
    public void deleteAdmin(String adminName) {
        if (adminRepository.existsById(adminName)) {
            adminRepository.deleteById(adminName);
            System.out.println("관리자 삭제 완료: " + adminName);
        } else {
            throw new IllegalArgumentException("해당 ID의 관리자를 찾을 수 없습니다: " + adminName);
        }
    }


    public AdminDTO getAdminById(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 관리자를 찾을 수 없습니다: " + adminId));

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName(admin.getAdminName()); // 관리자 이름 설정
        // 필요 시 추가 정보 설정 가능

        return adminDTO;
    }

    // 관리자 정보 업데이트
    public void updateAdmin(String AdminId, String newId, String newPassword) {
        Admin admin = adminRepository.findByAdminName(AdminId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 관리자를 찾을 수 없습니다: " + AdminId));

        if (AdminId != null && !AdminId.isEmpty()) {
            if (adminRepository.findByAdminId(AdminId).isPresent()) {
                throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
            }
            admin.setAdminId(newId);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            admin.setPassword(newPassword);
        }

        adminRepository.save(admin);
        System.out.println("관리자 정보 업데이트 완료: ");
    }

    // 의료진 관련 기능들
    public void registerDoctor(String name, String specialty, String contact) {
        validateDoctorDetails(name, specialty, contact);
        System.out.println("의료진 정보 등록: " + name + ", " + specialty + ", 연락처: " + contact);
    }

    public void updateDoctor(int doctorId, String name, String specialty, String contact) {
        validateDoctorDetails(name, specialty, contact);
        System.out.println("의료진 정보 수정: " + doctorId + ", " + name + ", " + specialty + ", 연락처: " + contact);
    }

    public void deleteDoctor(int doctorId) {
        System.out.println("의료진 정보 삭제: " + doctorId);
    }

    public void registerVacation(String doctorName, String startDate, String endDate) {
        validateDates(startDate, endDate);
        System.out.println("휴진 정보 등록: " + doctorName + ", " + startDate + " - " + endDate);
    }

    public void updateVacation(int vacationId, String startDate, String endDate) {
        validateDates(startDate, endDate);
        System.out.println("휴진 정보 수정: " + vacationId + ", " + startDate + " - " + endDate);
    }

    public void deleteVacation(int vacationId) {
        System.out.println("휴진 정보 삭제: " + vacationId);
    }

    // 모든 건의사항 목록 조회 (관리자 전용)
    public List<Feedback> getFeedbackList() {
        return feedbackRepository.findAll();
    }

    // 공지사항 등록
    public void registerNotice(String title, String writer, String email, String password,
                               Boolean isSecret, MultipartFile representativeImage, MultipartFile attachment,
                               String content, String link) throws IOException {
        // 파일 저장 경로 설정
        String imagePath = saveFile(representativeImage, "images");
        String attachmentPath = saveFile(attachment, "attachments");

        // Notice 객체 생성
        Notice notice = Notice.builder()
                .title(title)
                .writer(writer)
                .email(email)
                .password(password)
                .isSecret(isSecret)
                .imagePath(imagePath)
                .attachmentPath(attachmentPath)
                .content(content)
                .link(link)
                .status(isSecret ? "비공개" : "공고")
                .build();

        // 저장소에 저장
        noticeRepository.save(notice);
    }


    // 공지사항 조회
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    // 공지사항 삭제
    public void deleteNotices(List<Long> noticeIds) {
        noticeRepository.deleteAllById(noticeIds);
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile file, String directory) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String path = "uploads/" + directory + "/" + file.getOriginalFilename();
        File dest = new File(path);
        file.transferTo(dest);
        return path;
    }
    // 건강매거진 등록
    public void registerMagazine(String title, String content) {
        validateTitleAndContent(title, content);
        System.out.println("건강 매거진 등록: " + title);
    }
    // 건강매거진 수정
    public void updateMagazine(int magazineId, String title, String content) {
        validateTitleAndContent(title, content);
        System.out.println("건강 매거진 수정: " + magazineId);
    }
    // 건강매거진 삭제
    public void deleteMagazine(int magazineId) {
        System.out.println("건강 매거진 삭제: " + magazineId);
    }

    // 모든 진료 예약 조회 (관리자만 가능)
    public List<AppointmentDTO> getAllAppointments(String adminId, String password) {
        // 관리자 인증
        authenticateAdmin(adminId, password); // 관리자 계정 검증

        // 인증이 성공하면 모든 예약 정보를 반환
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO) // Appointment 엔티티를 AppointmentDTO로 변환
                .collect(Collectors.toList());
    }


    // DTO 생성 메서드
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .departmentId(appointment.getDepartment().getId())
                .patientName(appointment.getMember().getName()) // Member의 이름 가져오기
                .doctorName(appointment.getDoctor().getName()) // Doctor의 이름 가져오기
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .symptomDescription(appointment.getSymptomDescription())
                .build();
    }

    // 접수 목록 조회
    public List<OnlineRegistration> getRegistrationList() {
        return onlineRegistrationRepository.findAll();
    }

    // 환자 세부사항 조회
    public Optional<OnlineRegistration> getRegistrationDetails(Long registrationId) {
        return onlineRegistrationRepository.findById(registrationId);
    }


    // 모든 건강검진 예약 조회
    public List<HealthcareReserveDTO> getAllReservations() {
        return healthcareReserveService.getAllHealthReserves();
    }


    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (com.mallang.backend.domain.Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setMemberName(review.getMemberName());
            dto.setDoctorName(review.getDoctorName());
            dto.setDepartmentName(review.getDepartmentName());
            dto.setStar(review.getStar());
            dto.setDetailStar(review.getDetailStars()); // 세분화된 별점
            dto.setContent(review.getContent());
            dto.setAttachment(review.getFileUrl()); // 첨부파일 경로
            dto.setCreatedAt(review.getCreatedDate()); // 등록 시간
            reviewDTOList.add(dto);
        }

        return reviewDTOList;
    }

    // 특정 리뷰 삭제
    public void deleteReviewById(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");
        }
    }

    // 리뷰 통계 계산
    public Map<String, Object> calculateReviewStatistics() {
        List<ReviewDTO> reviews = getAllReviews();

        // 총 리뷰 수
        int totalReviews = reviews.size();

        // 전체 별점 평균
        double averageStar = reviews.stream()
                .mapToDouble(ReviewDTO::getStar)
                .average()
                .orElse(0.0);

        // 세분화된 별점 평균
        Map<Integer, Double> averageDetailStars = reviews.stream()
                .map(ReviewDTO::getDetailStar)  // ReviewDTO에서 세분화된 별점 가져오기
                .flatMap(List::stream)  // 각 ReviewDTO의 List<Integer>를 스트림으로 변환
                .collect(Collectors.groupingBy(
                        Integer::intValue,  // 각 별점 값을 Integer로 그룹핑
                        Collectors.averagingDouble(Integer::doubleValue)  // 평균 계산
                ));

        return Map.of(
                "totalReviews", totalReviews,
                "averageStar", averageStar,
                "averageDetailStars", averageDetailStars
        );
    }


    // 유효성 검사 - 의사 정보
    private void validateDoctorDetails(String name, String specialty, String contact) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("의료진 이름이 입력되지 않았습니다.");
        }
        if (specialty == null || specialty.isEmpty()) {
            throw new IllegalArgumentException("전문 분야를 입력해야 합니다.");
        }
        if (contact == null || contact.isEmpty()) {
            throw new IllegalArgumentException("연락처를 입력해야 합니다.");
        }
    }



    // 유효성 검사 - 제목 및 본문
    private void validateTitleAndContent(String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("본문을 입력해야 합니다.");
        }
    }

    // 유효성 검사 - 날짜
    private void validateDates(String startDate, String endDate) {
        if (startDate == null || startDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 시작일을 입력해야 합니다.");
        }
        if (endDate == null || endDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 종료일을 입력해야 합니다.");
        }
    }
}
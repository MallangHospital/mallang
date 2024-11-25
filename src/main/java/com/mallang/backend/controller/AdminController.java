package com.mallang.backend.controller;

import com.mallang.backend.dto.AdminMemberDTO;
import com.mallang.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService; // Lombok이 생성자를 자동 생성

    // 관리자 대시보드 정보 조회
    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "관리자 대시보드 정보"; // 실제 로직으로 대체
    }

    // 사용자 관리
    @PostMapping("/users")
    public String createUser(@RequestBody AdminMemberDTO memberDTO) {
        return "사용자가 생성되었습니다."; // 실제 로직으로 대체
    }

    // 사용자 정보 조회
    @GetMapping("/users/{username}")
    public AdminMemberDTO getUser(@PathVariable String username) {
        return new AdminMemberDTO(); // 실제 로직으로 대체
    }

    // 사용자 정보 수정
    @PutMapping("/users/{username}")
    public String updateUser(@PathVariable String username, @RequestBody AdminMemberDTO memberDTO) {
        return "사용자 정보가 수정되었습니다."; // 실제 로직으로 대체
    }

    // 사용자 삭제
    @DeleteMapping("/users/{username}")
    public String deleteUser(@PathVariable String username) {
        return "사용자가 삭제되었습니다."; // 실제 로직으로 대체
    }
}
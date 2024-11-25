package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 뉴스 ID

    private String name; // 작성자 이름
    private String password; // 비밀번호
    private String email; // 이메일
    private String website; // 홈페이지 URL

    private String title; // 뉴스 제목
    private String content; // 뉴스 내용

    @Column(name = "attachment1")
    private String attachment1; // 첨부파일 1 URL

    @Column(name = "attachment2")
    private String attachment2; // 첨부파일 2 URL

    private LocalDate writeDate; // 작성 날짜

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false) // Long 타입 adminId와 매핑
    private Admin admin;

    // 기본 생성자에서 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    public News(String name, String title, String content, String website, String newsDTOTitle, String newsDTOContent, String attachment1, String attachment2) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
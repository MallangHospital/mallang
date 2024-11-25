package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity

@Getter
@Setter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 공지사항 ID

    private String title;            // 공지사항 제목

    private String author;           // 작성자 이름

    private String email;            // 작성자 이메일

    private String password;         // 작성자 비밀번호

    private boolean isPrivate;       // 공개 여부 (true = 비공개, false = 공개)

    private String thumbnail;        // 대표 이미지

    private String attachment;       // 첨부 파일

    @Lob // 긴 텍스트를 저장하기 위한 어노테이션
    private String content;          // 본문

    private String link;             // 외부 링크

    private LocalDate writeDate;     // 작성 날짜

    // @PrePersist를 사용하여 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
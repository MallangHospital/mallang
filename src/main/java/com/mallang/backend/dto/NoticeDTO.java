package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDTO {
    private String id;
    private String title;
    private String content;
    private String author;      // 작성자
    private String email;       // 이메일
    private String password;    // 비밀번호
    private boolean isPrivate;  // 비밀글 여부
    private String thumbnail;   // 대표 이미지
    private String attachment;  // 첨부파일
    private String link;        // 링크
    private String writeDate;   // 작성 날짜 (LocalDate -> String으로 처리)
}
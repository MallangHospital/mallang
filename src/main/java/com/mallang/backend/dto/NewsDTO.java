package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private Long id;                // 뉴스 ID
    private String name;            // 작성자 이름
    private String password;        // 작성자 비밀번호
    private String email;           // 작성자 이메일
    private String website;         // 작성자 홈페이지
    private String title;           // 뉴스 제목
    private String content;         // 뉴스 내용
    private String attachment1;     // 첨부파일 1
    private String attachment2;     // 첨부파일 2
    private LocalDate writeDate;    // 작성 날짜
}
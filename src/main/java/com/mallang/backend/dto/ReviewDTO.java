package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long departmentId;
    private String departmentName;
    private Long doctorId;
    private Long doctorName;
    private Long memberName;
    private Long memberId; // 리뷰 작성자의 ID
    private Long memberPassword; // 리뷰 작성작의 Password
    private Long Star;
    private List<Integer> detailStar; // 세분화된 별점: [자세한 설명, 치료후 결과, 직원의 친절, 청결함]
    private String content; // 리뷰 내용
    private String attachment; // 첨부파일 (파일명 또는 경로)
    private Long createdAt; // 리뷰 등록 시간
}
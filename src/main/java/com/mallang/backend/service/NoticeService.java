package com.mallang.backend.service;

import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 최소화
public class NoticeService {

    private final NoticeRepository noticeRepository; // 생성자 주입

    // 모든 공지사항 조회
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> {
                    NoticeDTO dto = new NoticeDTO(); // 기본 생성자 사용
                    dto.setId(String.valueOf(notice.getId()));
                    dto.setTitle(notice.getTitle());
                    dto.setAuthor(notice.getWriter());
                    dto.setEmail(notice.getEmail());
                    dto.setPassword(notice.getPassword());
                    dto.setPrivate(notice.getIsSecret());
                    dto.setAttachment(notice.getAttachmentPath());
                    dto.setContent(notice.getContent());
                    dto.setLink(notice.getLink());
                    dto.setWriteDate(notice.getCreatedAt().toString()); // LocalDate -> String 변환
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 공지사항 생성
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice(); // 기본 생성자 사용
        notice.setTitle(noticeDTO.getTitle());
        notice.setWriter(noticeDTO.getAuthor());
        notice.setEmail(noticeDTO.getEmail());
        notice.setPassword(noticeDTO.getPassword());
        notice.setIsSecret(noticeDTO.isPrivate());
        notice.setAttachmentPath(noticeDTO.getAttachment());
        notice.setContent(noticeDTO.getContent());
        notice.setLink(noticeDTO.getLink());

        // 공지사항 저장
        notice = noticeRepository.save(notice);

        // NoticeDTO 반환
        NoticeDTO dto = new NoticeDTO();
        dto.setId(String.valueOf(notice.getId()));
        dto.setTitle(notice.getTitle());
        dto.setAuthor(notice.getWriter());
        dto.setEmail(notice.getEmail());
        dto.setPassword(notice.getPassword());
        dto.setPrivate(notice.getIsSecret());
        dto.setAttachment(notice.getAttachmentPath());
        dto.setContent(notice.getContent());
        dto.setLink(notice.getLink());
        dto.setWriteDate(notice.getCreatedAt().toString()); // LocalDate -> String 변환
        return dto;
    }
}
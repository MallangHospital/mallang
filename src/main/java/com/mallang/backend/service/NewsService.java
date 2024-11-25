package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class NewsService {

    private final NewsRepository newsRepository;

    /**
     * 모든 뉴스 가져오기
     * @return 뉴스 목록 DTO
     */
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> new NewsDTO(
                        news.getId(),
                        news.getName(),
                        news.getPassword(),
                        news.getEmail(),
                        news.getWebsite(),
                        news.getTitle(),
                        news.getContent(),
                        news.getAttachment1(),
                        news.getAttachment2(),
                        news.getWriteDate()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 뉴스 생성
     * @param newsDTO 생성할 뉴스 정보 DTO
     * @return 생성된 뉴스 DTO
     */
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = new News(
                newsDTO.getName(),
                newsDTO.getPassword(),
                newsDTO.getEmail(),
                newsDTO.getWebsite(),
                newsDTO.getTitle(),
                newsDTO.getContent(),
                newsDTO.getAttachment1(),
                newsDTO.getAttachment2()
        );
        news = newsRepository.save(news);
        return new NewsDTO(
                news.getId(),
                news.getName(),
                news.getPassword(),
                news.getEmail(),
                news.getWebsite(),
                news.getTitle(),
                news.getContent(),
                news.getAttachment1(),
                news.getAttachment2(),
                news.getWriteDate()
        );
    }
}
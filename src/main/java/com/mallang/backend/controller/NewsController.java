package com.mallang.backend.controller;

import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor // final 필드에 대해 자동 생성자 생성
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public List<NewsDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping
    public NewsDTO createNews(@RequestBody NewsDTO newsDTO) {
        return newsService.createNews(newsDTO);
    }
}
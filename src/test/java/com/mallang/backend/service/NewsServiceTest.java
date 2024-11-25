package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository; // Mock된 NewsRepository

    @InjectMocks
    private NewsService newsService; // Mock을 주입받은 NewsService

    @Test
    void testGetAllNews() {
        // Given: 테스트 데이터 준비
        News news1 = new News();
        news1.setName("John Doe");
        news1.setPassword("password123");
        news1.setEmail("john.doe@example.com");
        news1.setWebsite("http://example.com");
        news1.setTitle("Title 1");
        news1.setContent("Content 1");
        news1.setAttachment1("file1.png");
        news1.setAttachment2("file2.png");
        news1.setWriteDate(LocalDate.of(2024, 11, 17));

        News news2 = new News();
        news2.setName("Jane Doe");
        news2.setPassword("password456");
        news2.setEmail("jane.doe@example.com");
        news2.setWebsite("http://example2.com");
        news2.setTitle("Title 2");
        news2.setContent("Content 2");
        news2.setAttachment1("file3.png");
        news2.setAttachment2("file4.png");
        news2.setWriteDate(LocalDate.of(2024, 11, 18));

        // mock 설정: findAll 호출 시 뉴스 리스트 반환
        when(newsRepository.findAll()).thenReturn(Arrays.asList(news1, news2));

        // When: 서비스 메서드 호출
        List<NewsDTO> newsDTOList = newsService.getAllNews();

        // Then: 반환된 리스트 검증
        assertNotNull(newsDTOList);
        assertEquals(2, newsDTOList.size());

        // 첫 번째 NewsDTO 검증
        NewsDTO newsDTO1 = newsDTOList.get(0);
        assertEquals("John Doe", newsDTO1.getName());
        assertEquals("Title 1", newsDTO1.getTitle());
        assertEquals("Content 1", newsDTO1.getContent());
        assertEquals("file1.png", newsDTO1.getAttachment1());
        assertEquals("file2.png", newsDTO1.getAttachment2());
        assertEquals(LocalDate.of(2024, 11, 17), newsDTO1.getWriteDate());

        // 두 번째 NewsDTO 검증
        NewsDTO newsDTO2 = newsDTOList.get(1);
        assertEquals("Jane Doe", newsDTO2.getName());
        assertEquals("Title 2", newsDTO2.getTitle());
        assertEquals("Content 2", newsDTO2.getContent());
        assertEquals("file3.png", newsDTO2.getAttachment1());
        assertEquals("file4.png", newsDTO2.getAttachment2());
        assertEquals(LocalDate.of(2024, 11, 18), newsDTO2.getWriteDate());

        // Verify: findAll() 호출 확인
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void testCreateNews() {
        // Given: 테스트 데이터 준비
        NewsDTO newsDTO = new NewsDTO(
                null,
                "John Doe",
                "password123",
                "john.doe@example.com",
                "http://example.com",
                "New Title",
                "New Content",
                "file1.png",
                "file2.png",
                null // 작성 날짜는 서비스에서 설정됨
        );

        // 저장 후 반환될 News 객체 설정
        News savedNews = new News();
        savedNews.setId(1L);
        savedNews.setName("John Doe");
        savedNews.setPassword("password123");
        savedNews.setEmail("john.doe@example.com");
        savedNews.setWebsite("http://example.com");
        savedNews.setTitle("New Title");
        savedNews.setContent("New Content");
        savedNews.setAttachment1("file1.png");
        savedNews.setAttachment2("file2.png");
        savedNews.setWriteDate(LocalDate.of(2024, 11, 17));

        // mock 설정: save 호출 시 저장된 News 객체 반환
        when(newsRepository.save(any(News.class))).thenReturn(savedNews);

        // When: 서비스 메서드 호출
        NewsDTO createdNewsDTO = newsService.createNews(newsDTO);

        // Then: 반환된 NewsDTO 검증
        assertNotNull(createdNewsDTO);
        assertEquals(1L, createdNewsDTO.getId());
        assertEquals("John Doe", createdNewsDTO.getName());
        assertEquals("New Title", createdNewsDTO.getTitle());
        assertEquals("New Content", createdNewsDTO.getContent());
        assertEquals("file1.png", createdNewsDTO.getAttachment1());
        assertEquals("file2.png", createdNewsDTO.getAttachment2());
        assertEquals(LocalDate.of(2024, 11, 17), createdNewsDTO.getWriteDate());

        // Verify: save() 호출 확인
        verify(newsRepository, times(1)).save(any(News.class));
    }
}
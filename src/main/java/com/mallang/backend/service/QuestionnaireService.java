package com.mallang.backend.service;

import com.mallang.backend.domain.Question;
import com.mallang.backend.domain.Response;
import com.mallang.backend.dto.QuestionDTO;
import com.mallang.backend.dto.ResponseDTO;
import com.mallang.backend.repository.QuestionRepository;
import com.mallang.backend.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    // 문진표 질문 불러오기 (DTO 변환)
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(question -> QuestionDTO.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    // 문진표 응답 저장
    public void saveResponses(String memberId, List<ResponseDTO> responses) {
        List<Response> responseEntities = responses.stream()
                .map(dto -> Response.builder()
                        .memberId(memberId)
                        .question(Question.builder().id(dto.getQuestionId()).build()) // Lazy 로딩 방지
                        .answer(dto.getAnswer())
                        .build())
                .collect(Collectors.toList());

        responseRepository.saveAll(responseEntities);
    }

    // 특정 사용자의 문진표 응답 조회 (DTO 변환)
    public List<ResponseDTO> getResponsesByMemberId(String memberId) {
        List<Response> responses = responseRepository.findByMemberId(memberId);

        return responses.stream()
                .map(response -> ResponseDTO.builder()
                        .id(response.getId())
                        .questionId(response.getQuestion().getId())
                        .questionContent(response.getQuestion().getContent())
                        .answer(response.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }


    public List<ResponseDTO> getResponses(String memberId) {
        List<Response> responses = responseRepository.findByMemberId(memberId);

        return responses.stream()
                .map(response -> ResponseDTO.builder()
                        .id(response.getId())
                        .questionId(response.getQuestion().getId()) // questionId 설정
                        .questionContent(response.getQuestion().getContent())
                        .answer(response.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
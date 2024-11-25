package com.mallang.backend.service;

import com.mallang.backend.domain.Question;
import com.mallang.backend.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionInitializer {

    private final QuestionRepository questionRepository;

    @PostConstruct
    public void initializeQuestions() {
        if (questionRepository.count() == 0) {
            List<String> questions = Arrays.asList(
                    "나는 하루에 6시간 이하로 수면을 취하고 있다.",
                    "나는 일주일에 3번 이상 음주를 한다.",
                    "나는 주 1회 이상의 운동을 하지 않으며 활동량이 적다.",
                    "나는 하루 평균 2시간 이상 앉아 있는 시간이 많다.",
                    "나는 최근 체중이 급격히 증가하거나 감소하였다.",
                    "나는 자주 스트레스를 받거나 긴장 상태가 지속된다.",
                    "나는 하루 평균 2회 이상 간식을 섭취한다.",
                    "나는 최근 피로감을 자주 느낀다.",
                    "나는 규칙적인 식사 시간을 잘 지키고 있다.",
                    "나는 평소 짠 음식이나 단 음식을 자주 섭취한다.",
                    "나는 최근 기억력이 떨어지거나 집중하기 힘들다.",
                    "나는 주 1회 이상 흡연을 한다.",
                    "나는 가족 중에 고혈압, 당뇨, 심혈관 질환 병력이 있다.",
                    "나는 최근 두통이나 어지러움을 자주 경험하였다.",
                    "나는 평소 물 섭취량이 하루 1리터 미만이다."
            );

            questions.forEach(question -> questionRepository.save(
                    Question.builder().content(question).build()));
        }
    }
}
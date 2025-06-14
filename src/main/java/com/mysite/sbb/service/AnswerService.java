package com.mysite.sbb.service;

import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.Answer;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = Answer.builder()
            .content(content)
            .question(question)
            // .createDate(LocalDateTime.now())
            .build();
        this.answerRepository.save(answer);
    }
}

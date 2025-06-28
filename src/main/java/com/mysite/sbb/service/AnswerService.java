package com.mysite.sbb.service;

import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.Answer;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.domain.UserEntity;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    // 답변 저장
    public void create(Question question, String content, UserEntity author) {
        Answer answer = Answer.builder()
            .content(content)
            .question(question)
            // .createDate(LocalDateTime.now())
            .author(author)
            .build();
        this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        return this.answerRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("answer not found"));
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
}

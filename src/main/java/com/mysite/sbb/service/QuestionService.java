package com.mysite.sbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.Question;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 질문입니다."));
    }
}

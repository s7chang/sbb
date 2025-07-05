package com.mysite.sbb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.domain.Question;
import com.mysite.sbb.domain.UserEntity;
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

    public void create(String subject, String content, UserEntity author) {
        Question q = Question.builder()
            .subject(subject)
            .content(content)
            .author(author)
            .build();
        this.questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content) {
        // 실제로 내용이 변경되었는지 확인
        boolean isChanged = !question.getSubject().equals(subject) || !question.getContent().equals(content);
        
        question.setSubject(subject);
        question.setContent(content);
        
        // 실제로 변경되었을 때만 수정 횟수 증가
        if (isChanged) {
            question.setModifyCount(question.getModifyCount() + 1);
        }
        
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    // 페이지 처리
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    // 추천 기능
    public void vote(Question question, UserEntity user) {
        if (question.getVoter().contains(user)) {
            question.getVoter().remove(user);
        } else {
            question.getVoter().add(user);
        }
        this.questionRepository.save(question);
    }
}

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
    public Answer create(Question question, String content, UserEntity author) {
        Answer answer = Answer.builder()
            .content(content)
            .question(question)
            // .createDate(LocalDateTime.now())
            .author(author)
            .build();
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        return this.answerRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("answer not found"));
    }

    public void modify(Answer answer, String content) {
        // 실제로 내용이 변경되었는지 확인
        boolean isChanged = !answer.getContent().equals(content);
        
        answer.setContent(content);
        
        // 실제로 변경되었을 때만 수정 횟수 증가
        if (isChanged) {
            answer.setModifyCount(answer.getModifyCount() + 1);
        }
        
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    // 추천 기능
    public void vote(Answer answer, UserEntity user) {
        if (answer.getVoter().contains(user)) {
            answer.getVoter().remove(user);
        } else {
            answer.getVoter().add(user);
        }
        this.answerRepository.save(answer);
    }
}

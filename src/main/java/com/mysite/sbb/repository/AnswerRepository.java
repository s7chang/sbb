package com.mysite.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}

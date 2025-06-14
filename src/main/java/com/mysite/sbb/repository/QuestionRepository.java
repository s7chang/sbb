package com.mysite.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.domain.Question;

// 레파지토리 인터페이스는 JpaRepository 인터페이스를 상속하여 만든다.
// JpaRepository<레파지토리로 사용할 엔터티명, 레파지토리로 사용할 엔터티의 기본키 @Id 필드의 타입>
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// 1. subject 필드를 통해 데이터 조회
	Question findBySubject(String subject);
}

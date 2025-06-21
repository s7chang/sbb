package com.mysite.sbb;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.service.QuestionService;

import lombok.extern.java.Log;

// 레파지토리 테스트 클래스
@Log
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SbbApplicationTests {

	// QuestionRepository를 bean으로 등록
	// Bean(콩) - 자바의 인스턴스 (객체), 스프링부트가 자동으로 등록하고 관리하는 객체
	// @Autowired
	// private QuestionRepository questionRepository;

	@Autowired
    private QuestionService questionService;

	@Test
	public void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}

// 	// 1. 데이터를 테이블에 저장 -> insert 문을 생성
// 	@Test
// 	public void testJpaInsert() {
// 		Question q1 = new Question();
// 		q1.setSubject("JPA란 무엇인가?");
// 		q1.setContent("JPA는 스프링부트의 ORM 기술이다.");
// 		q1.setCreateDate(LocalDateTime.now());
// 		questionRepository.save(q1);

// 		Question q2 = new Question();
// 		q2.setSubject("Lombok이란 무엇인가?");
// 		q2.setContent("Lombok은 클래스의 생성자와 메서드를 자동으로 생성해주는 라이브러리이다.");
// 		q2.setCreateDate(LocalDateTime.now());
// 		questionRepository.save(q2);

// 		Question q3 = new Question();
// 		q3.setSubject("Entity란 무엇인가?");
// 		q3.setContent("Entity는 데이터베이스의 테이블과 매핑되는 클래스이다.");
// 		q3.setCreateDate(LocalDateTime.now());
// 		questionRepository.save(q3);

// 		Question q4 = new Question();
// 		q4.setSubject("Thymeleaf란 무엇인가?");
// 		q4.setContent("Thymeleaf는 타임리프는 스프링부트의 템플릿 엔진이다.");
// 		q4.setCreateDate(LocalDateTime.now());
// 		questionRepository.save(q4);

// 		Question q5 = new Question();
// 		q5.setSubject("QueryDSL이란 무엇인가?");
// 		q5.setContent("QueryDSL은 스프링부트의 ORM 기술이다.");
// 		q5.setCreateDate(LocalDateTime.now());
// 		questionRepository.save(q5);

// 	}

// 	// 2. 테이블의 모든 데이터를 조회 -> select 문을 생성
// //	@Test
// 	public void testJpaSelectAll() {
// 		List<Question> questionAll = questionRepository.findAll();
// 		assertEquals(2, questionAll.size());
		
// 		Question q = questionAll.get(0);
// 		assertEquals("sbb가 무엇인가요?", q.getSubject());
// 	}

// 	// 3. 테이블의 기본 키를 통한 데이터 조회 -> select 문을 생성
// //	@Test
// 	public void testJpaSelectById() {
// //		Question q = questionRepository.findById(1).get();
// 		Optional<Question> oq = questionRepository.findById(1);

// 		if (oq.isPresent()) {
// 			Question q = oq.get();
// 			assertEquals("sbb가 무엇인가요?", q.getSubject());
// 		}
// 	}

// 	// 4. 테이블에서 기본 키가 아닌 필드를 조건으로 데이터 조회 -> select 문을 생성
// 	// subject 필드를 사용하여 데이터 조회
// //	@Test
// 	public void testJpaSelectBySubject() {
// 		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
// 		assertEquals(1, q.getId());
// 	}

}

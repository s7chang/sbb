package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.domain.Question;
import com.mysite.sbb.repository.QuestionRepository;

// 레파지토리 테스트 클래스
//@SpringBootTest
class SbbApplicationTests {

	// QuestionRepository를 bean으로 등록
	// Bean(콩) - 자바의 인스턴스 (객체), 스프링부트가 자동으로 등록하고 관리하는 객체
	@Autowired
	private QuestionRepository questionRepository;

	// 1. 데이터를 테이블에 저장 -> insert 문을 생성
//	@Test
	public void testJpaInsert() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}

	// 2. 테이블의 모든 데이터를 조회 -> select 문을 생성
//	@Test
	public void testJpaSelectAll() {
		List<Question> questionAll = questionRepository.findAll();
		assertEquals(2, questionAll.size());
		
		Question q = questionAll.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	// 3. 테이블의 기본 키를 통한 데이터 조회 -> select 문을 생성
//	@Test
	public void testJpaSelectById() {
//		Question q = questionRepository.findById(1).get();
		Optional<Question> oq = questionRepository.findById(1);

		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	// 4. 테이블에서 기본 키가 아닌 필드를 조건으로 데이터 조회 -> select 문을 생성
	// subject 필드를 사용하여 데이터 조회
//	@Test
	public void testJpaSelectBySubject() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

}

package com.mysite.sbb.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

// Entity 클래스는 JPA에 의해 자동으로 데이터베이스의 테이블로 생성된다.
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Question {
	// 테이블의 primary key 로 설정되는 필드
	// 자동으로 일련번호를 생성한다 -> Oracle의 sequence, MySQL의 auto_incremet 역할)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	// 자동으로 현재 시스템의 날짜와 시간으로 설정
	@CreatedDate
	private LocalDateTime createDate;

	// ★★★★★
	// 질문 1개에 대한 답변은 여러 개가 생성됨
	// 질문에 대한 답변은 리스트로 생성함.
	// 1:N의 관계가 형성
	// mappedBy는 Answer 엔터티의 question
	// CascadeType.REMOVE는 질문을 삭제하면 자동으로(강제적으로) 그 질문에 대한 답변도 모두 삭제한다. 
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
}

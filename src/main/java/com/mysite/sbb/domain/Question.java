package com.mysite.sbb.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entity 클래스는 JPA에 의해 자동으로 데이터베이스의 테이블로 생성된다.
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
	// 테이블의 primary key 로 설정되는 필드
	// 자동으로 일련번호를 생성한다 -> Oracle의 sequence, MySQL의 auto_incremet 역할)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "제목은 필수 항목입니다.")
	// @Size(max = 200, message = "제목은 200자 이하로 입력해주세요.")
	@Size(max = 200)
	@Column(length = 200)
	private String subject;

	@NotBlank(message = "내용은 필수 항목입니다.")
	@Column(columnDefinition = "TEXT")
	private String content;

	// 생성시각
	// 자동으로 현재 시스템의 날짜와 시간으로 설정
	@CreatedDate
	@Column(columnDefinition = "datetime default now()")
	private LocalDateTime createDate;

	// 수정시각
	// 자동으로 현재 시스템의 날짜와 시간으로 설정
	@LastModifiedDate
	@Column(columnDefinition = "datetime default now()")
	private LocalDateTime modifyDate;

	// 수정 횟수
	@Builder.Default
	@Column(columnDefinition = "integer default 0")
	private Integer modifyCount = 0;

	// 추천한 사용자 목록 (ManyToMany 관계)
	@ManyToMany
	@JoinTable(name = "question_voter")
	private Set<UserEntity> voter;

	// ★★★★★
	// 질문 1개에 대한 답변은 여러 개가 생성됨
	// 질문에 대한 답변은 리스트로 생성함.
	// 1:N의 관계가 형성
	// mappedBy는 Answer 엔터티의 question
	// CascadeType.REMOVE는 질문을 삭제하면 자동으로(강제적으로) 그 질문에 대한 답변도 모두 삭제한다. 
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;

	// 작성자 추가
	@ManyToOne
	private UserEntity author;
}

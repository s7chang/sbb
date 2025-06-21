package com.mysite.sbb.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "내용은 필수 항목입니다.")
	@Column(columnDefinition = "TEXT")
	private String content;

	// 자동으로 현재 시스템의 날짜와 시간으로 설정
	@CreatedDate
	@Column(columnDefinition = "datetime default now()")
	private LocalDateTime createDate;

	// ★★★★★
	// 답변은 여러 개, 질문은 1 개 -> 질문 1개에 답변은 여러 개가 생성됨
	// N:1 관계를 설정 (외래키가 설정될 것이다)
	@ManyToOne
	private Question question;
}

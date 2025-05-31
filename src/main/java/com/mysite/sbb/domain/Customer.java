package com.mysite.sbb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 엔터티 클래스
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor()
public class Customer {
	// 기본키, MySQL에서는 auto_increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition="varchar(30)")
	private String customerId;

	@Column(columnDefinition="varchar(50)")
	private String name;

	@Column(columnDefinition="varchar(10) default 'female'")
	private String gender;

	private Integer age;

	@Column(columnDefinition="varchar(17)")
	private String phone;

	private String address;
}

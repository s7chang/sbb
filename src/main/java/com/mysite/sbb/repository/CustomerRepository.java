package com.mysite.sbb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	// findBy변수명(변수값) -> 변수명에 대한 데이터 조회, 리턴타입은 엔터티(1건)
	Optional<Customer> findByCustomerId(String customerId);
	Optional<Customer> findByName(String name);
	// findBy변수명(변수값) -> 변수명에 대한 모든 데이터 조회, 리턴타입은 엔터티의 리스트(여러 건)
	List<Customer> findByGender(String gender);
	// deleteBy변수명(변수값) -> 변수명에 대한 모든 데이터를 삭제, 리턴타입은 삭제된 엔터티의 개수
	Integer deleteByName(String name);
	// existsBy변수명(변수값) -> 변수명에 대한 데이터가 존재하는 지의 여부, 리턴타입은 boolean
	boolean existsByName(String name);
	// countBy변수명(변수값) -> 변수명에 대한 데이터의 개수
	Integer countByGender(String gender);

	// 조건 키워드
	Optional<Customer> findByNameAndAge(String name, Integer age);
	List<Customer> findByAgeGreaterThanEqualAndAgeLessThan(Integer ageMin, Integer ageMax);
	List<Customer> findByAgeBetween(Integer ageMin, Integer ageMax);
	List<Customer> findByAgeOrAgeOrAge(Integer age1, Integer age2, Integer age3);
	List<Customer> findByAgeIn(List<Integer> ageList);
	List<Customer> findByAgeNotIn(List<Integer> ageList);
}

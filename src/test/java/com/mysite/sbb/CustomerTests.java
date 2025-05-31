package com.mysite.sbb;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.domain.Customer;
import com.mysite.sbb.repository.CustomerRepository;

import lombok.extern.java.Log;

// Customer 테이블의 CRUD를 테스트하는 클래스
// Customer Entity 클래스에 대한 Repository를 활용
@Log
@SpringBootTest
public class CustomerTests {

	// 빈으로 사용한 repository 인터페이스 등록
	@Autowired
	private CustomerRepository customerRepository;

	// 1번 - customer 테이블의 모든 데이터를 조회
	@Test
	public void testSelectAll() {
		List<Customer> customerList = customerRepository.findAll();
		
//		log.info(customerList.toString());
		
		for (Customer customer : customerList) {
			log.info(customer.toString());
		}
	}
	
	// 2번 - customer 테이블에서 id가 3인 데이터를 조회 
//	@Test
	public void testSelectOne() {
		// 스프링부트에서는 객체를 가져올 때 Optional 클래스를 감싸서 가져옴.
		// -> NullPointerException을 방지하기 위해서
		Optional<Customer> o = customerRepository.findById(3L);
		if (o.isPresent()) {
			Customer customer = o.get();
			log.info(customer.toString());
		}
		else {
			log.warning("Failed to find by id 3L");
		}
	}

	// 3번 - customer 테이블에서 id가 4인 데이터를 삭제 
//	@Test
	public void testDeleteOne() {
		customerRepository.deleteById(4L);
		// 스프링부트에서는 객체를 가져올 때 Optional 클래스를 감싸서 가져옴.
		// -> NullPointerException을 방지하기 위해서
		Optional<Customer> o = customerRepository.findById(4L);
		if (o.isPresent()) {
			Customer customer = o.get();
			log.info(customer.toString());
		}
		else {
			log.warning("Failed to find by id 4L");
		}
	}

	// 4번 - customer 테이블의 모든 데이터를 삭제 
//	@Test
	public void testDeleteAll() {
		customerRepository.deleteAll();
		List<Customer> customerList = customerRepository.findAll();
		if (customerList.size() == 0) {
			log.warning("Failed to select all");
		}
	}

	// 5번 - customer 테이블에
	// 'jgn', '장강남', 'male', 18, '010-1234-5678', '서울시 강남구 압구정동'
	@Test
	public void testInsertOn() {
		Customer customer = Customer.builder()
								.customerId("jgn")
								.name("장강남")
								.gender("male")
								.age(18)
								.phone("010-1234-5678")
								.address("서울시 강남구 압구정동")
							.build();
//		customer.setCustomerId("jgn");
//		customer.setName("장강남");
//		customer.setGender("male");
//		customer.setAge(18);
//		customer.setPhone("010-1234-5678");
//		customer.setAddress("서울시 강남구 압구정동");
		customerRepository.save(customer);
	}
}

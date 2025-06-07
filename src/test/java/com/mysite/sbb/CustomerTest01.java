package com.mysite.sbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.domain.Customer;
import com.mysite.sbb.repository.CustomerRepository;

import lombok.extern.java.Log;

// Customer 테이블의 CRUD를 테스트하는 클래스
// Customer Entity 클래스에 대한 Repository를 활용
@Log
// @SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  // application-test.yml 사용
@Transactional
@Rollback(false)
public class CustomerTest01 {

	// 빈으로 사용한 repository 인터페이스 등록
	@Autowired
	private CustomerRepository customerRepository;

	// 1번 - customer 테이블의 모든 데이터를 조회
//	@Test
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
		Optional<Customer> customer = customerRepository.findById(3L);
		if (customer.isPresent()) {
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
		Optional<Customer> customer = customerRepository.findById(4L);
		if (customer.isPresent()) {
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
//	@Test
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

	// 5번 - customer 테이블에
	// 'jgn', '장강남', 'male', 18, '010-1234-5678', '서울시 강남구 압구정동'
	// 'oho', '오하영', 28, '010-4321-8888', '서울시 구로구 가리봉동'
	// 'uji', '엄재일', 'male', 38, '010-3333-3333', '인천시 북구 월미동'
	// 'ksb', '김사비', 'female', 19, '010-4444-7985', '경기도 성남시 중구 가구동'
	// 'hdh', '한동훈', 'male', 33, '010-5151-3461', '전라북도 전주시'
//	@Test
	public void testInsertAll() {
		List<Customer> customerList = List.of(
			Customer.builder().customerId("jgn").name("장강남").gender("male").age(18)
				.phone("010-1234-5678").address("서울시 강남구 압구정동").build(),
	
		    Customer.builder().customerId("oho").name("오하영").age(28)
				.phone("010-4321-8888").address("서울시 구로구 가리봉동").build(),
	
	        Customer.builder().customerId("uji").name("엄재일").gender("male").age(38)
				.address("인천시 북구 월미동").build(),
	
	        Customer.builder().customerId("ksb").name("김사비").gender("female").age(19)
				.phone("010-4444-7985").address("경기도 성남시 중구 가구동").build(),
	
	        Customer.builder().customerId("hdh").name("한동훈").gender("male").age(33)
				.address("전라북도 전주시").build(),

	        Customer.builder().customerId("hni").name("하나일").gender("male").age(39)
				.phone("010-5151-3461").address("전라북도 전주시").build(),

			Customer.builder().customerId("jjj").name("장정장").gender("male").age(33)
				.phone("010-5151-3461").address("전라북도 전주시").build(),

			Customer.builder().customerId("ddd").name("도대두").gender("male").age(33)
				.phone("010-5151-3461").address("전라북도 전주시").build()

		);

		List<Customer> insertList = new ArrayList<>();
		for (Customer customer : customerList) {
			log.info("이전: " + customer.toString());

			if (customerRepository.findByCustomerId(customer.getCustomerId()).isEmpty()) {
				insertList.add(customer);
			}
		}

		customerRepository.saveAll(insertList);

		for (Customer customer : insertList) {
			log.info("이후: " + customer.toString());
		}
	}

	// 6번 - count
//	@Test
	public void testCount() {
		log.info("개수: " + customerRepository.count());
	}

	// 7번 - exist
//	@Test
	public void testExist() {
		boolean isExist = customerRepository.existsById(37L);
		log.info("37번 존재?: " + isExist);
	}

	///////////////////////////////////////////////////////////////////////
	// customerId로 조회 (1건)
//	@Test
	@DisplayName("customerId로 조회 (1건)")
	public void testSelectByCustomerId() {
		String id = "oho";
		Optional<Customer> customer = customerRepository.findByCustomerId(id);
		
		if (customer.isPresent()) {
			log.info(customer.toString());
		}
		else {
			log.info("customerId가 " + id + "인 고객이 없습니다.");
		}
	}

	// gender로 모두 조회
//	@Test
	@DisplayName("gender로 모두 조회")
	public void testSelectByGender() {
		String gender = "female";
		List<Customer> customerList = customerRepository.findByGender(gender);
		
		for (Customer customer : customerList) {
			log.info("성별이 " + gender + "인 사람: " + customer.toString());
		}
	}

	// 이름이 한동훈이고 나이가 33인 사람 조회 (1건)
//	@Test
	@DisplayName("이름이 한동훈이고 나이가 33인 사람 조회 (1건)")
	public void testSelectByNameAndAge() {
		String name = "한동훈";
		Integer age = 33;
		Optional<Customer> customer = customerRepository.findByNameAndAge(name, age);
		
		if (customer.isPresent()) {
			log.info("이름이 " + name + "이고 나이가 " + age + "인 사람: " + customer.toString());
		}
		else {
			log.warning("이름이 " + name + "이고 나이가 " + age + "인 사람이 없습니다.");
		}
	}


	// 
	// @Test
	public void testVariableCondition() {
		testInsertAll();
		// List<Customer> customerList1 = customerRepository.findByAgeBetween(20, 29);
		// List<Customer> customerList2 = customerRepository.findByAgeOrAgeOrAge(18, 28, 30);
		// List<Customer> customerList3 = customerRepository.findByAgeIn(List.of(18, 28, 30));
		// List<Customer> customerList4 = customerRepository.findByAgeNotIn(List.of(18, 28, 30));
		List<Customer> customerList5 = customerRepository.findByAgeGreaterThanEqualOrderByName(30);
		List<Customer> customerList6 = customerRepository.findByAddressContainingOrderByAgeDesc("서울");
		List<Customer> customerList7 = customerRepository.findFirst2ByOrderById();
		List<Customer> customerList8 = customerRepository.findTop3ByAgeOrderById(33);

		// for (Customer customer : customerList1) {
		// 	log.info("20대인 사람: " + customer.toString());
		// }
		
//		for (Customer customer : customerList2) {
//			log.info("18, 28, 33인 사람: " + customer.toString());
//		}
		
		// customerList2.forEach(c -> log.info("findByAgeOrAgeOrAge: 18, 28, 33인 사람: " + c.toString()));
		// customerList3.forEach(c -> log.info("findByAgeIn: 18, 28, 33인 사람: " + c.toString()));
		// customerList4.forEach(c -> log.info("findByAgeNotIn: 18, 28, 33이 아닌 사람: " + c.toString()));
		customerList5.forEach(c -> log.info("findByAgeGreaterThanEqualOrderByName: 30세 이상인 사람: " + c.toString()));
		customerList6.forEach(c -> log.info("findByAddressContainingOrderByAgeDesc: 서울에 사는 사람: " + c.toString()));
		customerList7.forEach(c -> log.info("findFirst2ByOrderById: 아이디 순으로 2명: " + c.toString()));
		customerList8.forEach(c -> log.info("findTop3ByAgeOrderById: 나이가 33살인 사람을 아이디 순으로 3명: " + c.toString()));

		// customerRepository.deleteByPhoneIsNull();
	}

	@DisplayName("id가 4인 고객의 나이를 45로 수정")
	@Test
	public void updateAge() {
		testInsertAll();

		customerRepository.findById(4L).ifPresent(customer -> {
			customer.setAge(45);
		});
	}
}

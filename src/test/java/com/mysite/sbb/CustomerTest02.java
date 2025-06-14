package com.mysite.sbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.mysite.sbb.domain.Customer;
import com.mysite.sbb.repository.CustomerRepository2;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Log
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  // application-test.yml 사용
@Transactional
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest02 {
	// 빈으로 사용한 repository 인터페이스 등록
	@Autowired
	private CustomerRepository2 customerRepository;

    @DisplayName("초기화")
    @Test
    @Order(0)
    public void init() {
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

        customerList.forEach(c -> log.info("이전: " + c.toString()));

        List<Customer> newCustomerList = new ArrayList<Customer>();
        customerList.forEach(c -> {
            Optional<Customer> customer = customerRepository.findByCustomerId(c.getCustomerId());
            if (customer.isPresent()) {
                customer.get().setModifyDate(null);
                newCustomerList.add(customer.get());
            }
            else {
                newCustomerList.add(c);
            }
        });
		customerRepository.saveAll(newCustomerList);
        newCustomerList.forEach(c -> log.info("이후: " + c.toString()));
    }

    @Test
    @Order(100)
    void deleteByName() {
        log.info("삭제 시작");
        customerRepository.deleteByName("엄재일");

        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(c -> log.info("남은 데이터: " + c.toString()));

        log.info("삭제 종료");
    }

    @Test
    @Order(100)
    @Transactional
    @Commit
    void deleteById2() {
        Customer customer = Customer.builder().id(3L).build();
        customerRepository.deleteById2(customer);

        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(c -> log.info("남은 데이터: " + c.toString()));
    }
}

package com.mysite.sbb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.domain.Customer;

public interface CustomerRepository2 extends JpaRepository<Customer, Long> {
	// final String REPLACE_QUERY = """
    //     UPDATE Customer c
    //     SET c.name = :#{#customer.name},
    //         c.gender = :#{#customer.gender},
    //         c.age = :#{#customer.age},
    //         c.phone = :#{#customer.phone},
    //         c.address = :#{#customer.address},
    //         c.modifyDate = CURRENT_TIMESTAMP
    //     WHERE c.customerId = :#{#customer.customerId}
    // """;
	// @Query(value=REPLACE_QUERY)
	// @Transactional
	// @Modifying
	// Integer update(@Param("customer") Customer customer);

	// final String SELECT_ID = "select c from Customer c where c.id = :id";
	// @Query(value=SELECT_ID)
	// Optional<Customer> selectById(@Param("id") Long id);

	Optional<Customer> findByCustomerId(String customerId);
	void deleteByName(String name);
}

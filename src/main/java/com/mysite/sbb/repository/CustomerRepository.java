package com.mysite.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

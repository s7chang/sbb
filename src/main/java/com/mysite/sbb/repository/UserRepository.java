package com.mysite.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.domain.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {

}

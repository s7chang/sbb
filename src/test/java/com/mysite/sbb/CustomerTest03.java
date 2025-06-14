package com.mysite.sbb;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.mysite.sbb.domain.Customer;
import com.mysite.sbb.domain.QCustomer;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Log
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  // application-test.yml 사용
@Transactional
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest03 {
    // @Autowired
    // CustomerRepository customerRepository;

    // 엔터티를 관리하는 객체
    @PersistenceContext
    EntityManager em;

    // 1. 데이터 조회
    @DisplayName("1. 데이터 조회")
    @Test
    public void testSelectAll() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).fetch();
        customerList.forEach(c -> log.info("1: " + c.toString()));
    }

    // 2. 아이디로 데이터 조회
    @DisplayName("2. 아이디로 데이터 조회")
    @Test
    public void testSelectById() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(4L)).fetchOne();
        log.info("2: " + customer.toString());
    }

    // 3. 고객 아이디가 uji이고 이름이 엄재일인 데이터 조회
    @DisplayName("3. 고객 아이디가 uji이고 이름이 엄재일인 데이터 조회")
    @Test
    public void testSelectByCustomerIdAndName() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        Customer customer = factory.selectFrom(qCustomer).where(qCustomer.customerId.eq("uji").and(qCustomer.name.eq("엄재일"))).fetchOne();
        log.info("3: " + customer.toString());
    }

    // 4. 성별이 여성이거나 나이가 30세 이하인 데이터 조회
    @DisplayName("4. 성별이 여성이거나 나이가 30세 이하인 데이터 조회")
    @Test
    public void testSelectByGenderAndAge() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.gender.eq("Female").or(qCustomer.age.loe(30))).fetch();
        customerList.forEach(c -> log.info("4: " + c.toString()));
    }

    // 5. 성별이 여성이거나 나이가 30세 이하인 데이터를 나이를 기준으로 내림차순으로 조회
    @DisplayName("5. 성별이 여성이거나 나이가 30세 이하인 데이터를 나이를 기준으로 내림차순으로 조회")
    @Test
    public void testSelectByGenderAndAgeDesc() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.gender.eq("Female").or(qCustomer.age.loe(30))
            ).orderBy(qCustomer.age.desc()
        ).fetch();
        customerList.forEach(c -> log.info("5: " + c.toString()));
    }

    // 6. 나이가 10대인 데이터를 나이의 오름차순으로 조회
    @DisplayName("6. 나이가 10대인 데이터를 나이의 오름차순으로 조회")
    @Test
    public void testSelectByAgeRange() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.age.goe(10).and(qCustomer.age.lt(20))
            ).orderBy(
                qCustomer.age.asc()
            ).fetch();
        customerList.forEach(c -> log.info("6-1: " + c.toString()));

        log.info("--------------------------------");

        List<Customer> customerList2 = factory.selectFrom(qCustomer)
            .where(
                qCustomer.age.between(10, 19)
            ).orderBy(
                qCustomer.age.asc()
            ).fetch();
        customerList2.forEach(c -> log.info("6-2: " + c.toString()));
    }

    // 7. 나이가 18, 28, 30인 데이터를 조회
    @DisplayName("7. 나이가 18, 28, 30인 데이터를 조회")
    @Test
    public void testSelectByAge() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.age.eq(18).or(qCustomer.age.eq(28)).or(qCustomer.age.eq(30))
            ).fetch();
        customerList.forEach(c -> log.info("7-1: " + c.toString()));

        log.info("--------------------------------");

        List<Customer> customerList2 = factory.selectFrom(qCustomer)
            .where(
                qCustomer.age.in(18, 28, 30)
            ).fetch();
        customerList2.forEach(c -> log.info("7-2: " + c.toString()));
    }

    // 8. 이름에 '사'를 포함하는 데이터를 조회
    @DisplayName("8. 이름에 '사'를 포함하는 데이터를 조회")
    @Test
    public void testSelectByName() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.name.contains("사")
            ).fetch();
        customerList.forEach(c -> log.info("8: " + c.toString()));
    }

    // 9. 이름이 '한'으로 시작하는 데이터를 조회
    @DisplayName("9. 이름이 '한'으로 시작하는 데이터를 조회")
    @Test
    public void testSelectByNameStartWith() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.name.startsWith("한")
            ).fetch();
        customerList.forEach(c -> log.info("9: " + c.toString()));
    }

    // 10. 이름이 '남'으로 끝나는 데이터를 조회
    @DisplayName("10. 이름이 '남'으로 끝나는 데이터를 조회")
    @Test
    public void testSelectByNameEndWith() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
            .where(
                qCustomer.name.endsWith("남")
            ).fetch();
        customerList.forEach(c -> log.info("10: " + c.toString()));
    }

    // 11. id가 2인 고객의 이름과 전화번호를 수정
    @DisplayName("11. id가 2인 고객의 이름과 전화번호를 수정")
    @Test
    public void testUpdateNameAndPhone() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        long updatedCount = factory.update(qCustomer)
            .set(qCustomer.name, "기온미")
            .set(qCustomer.phone, "010-9999-9999")
            .where(qCustomer.id.eq(2L))
        .execute();

        log.info("11: " + updatedCount);

        Customer customer = factory.selectFrom(qCustomer)
            .where(qCustomer.id.eq(2L))
        .fetchOne();

        log.info("11: " + customer.toString());
    }

    // 12. customerIdd가 hdh인 데이터를 삭제
    @DisplayName("12. customerIdd가 hdh인 데이터를 삭제")
    @Test
    public void testDeleteByCustomerId() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // 엔터티를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        long deletedCount = factory.delete(qCustomer)
            .where(qCustomer.customerId.eq("hdh"))
        .execute();

        log.info("12: " + deletedCount);

        List<Customer> customerList = factory.selectFrom(qCustomer).fetch();
        customerList.forEach(c -> log.info("12: " + c.toString()));
    }

}

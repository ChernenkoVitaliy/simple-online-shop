package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);

    @Query("SELECT c FROM Customer c INNER JOIN c.account a WHERE a.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);
}

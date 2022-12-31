package com.shop.online.simple.repository;


import com.shop.online.simple.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findOne(long id);

    List<Customer> findAll();

    void save(Customer customer);

    void update(Customer customer);
}

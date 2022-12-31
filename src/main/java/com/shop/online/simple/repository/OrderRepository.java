package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findOne(long id);

    List<Order> findAll();

    void save(Order order);

    void delete(Order Order);
}

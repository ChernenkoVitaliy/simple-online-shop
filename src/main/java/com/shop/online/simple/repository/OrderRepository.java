package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

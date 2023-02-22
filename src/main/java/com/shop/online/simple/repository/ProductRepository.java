package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByNameContainsIgnoreCase(String name);
}

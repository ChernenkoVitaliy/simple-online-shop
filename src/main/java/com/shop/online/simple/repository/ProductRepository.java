package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContainsIgnoreCase(String name);

    List<Product> findAllBySeller(Seller seller);

    @Query("SELECT p FROM Product p INNER JOIN p.tags t WHERE t = :tag")
    List<Product> findAllByTag(@Param("tag") Tag tag);
}

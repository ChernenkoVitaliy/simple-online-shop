package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findOne(long id);

    List<Product> findAll();

    void save(Product product, Seller seller);

    void update(Product product);

    void delete(Product product);

    List<Product> findProductsBySeller(Seller seller);

    List<Product> findProductsByTag(Tag tag);

    List<Product> findProductByName(String name);

}

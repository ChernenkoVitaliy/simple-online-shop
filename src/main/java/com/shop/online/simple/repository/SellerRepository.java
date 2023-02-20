package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerRepository {
    Optional<Seller> findOne(long id);

    List<Seller> findAll();

    void save(Seller seller);

    void update(Seller seller);
}

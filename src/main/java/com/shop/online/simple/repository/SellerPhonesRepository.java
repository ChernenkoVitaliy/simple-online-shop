package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Seller;

import java.util.List;

public interface SellerPhonesRepository {

    List<String> findAll();

    void save(String number, Seller seller);

    void delete(String number);

    List<String> findBySellerId(long id);
}

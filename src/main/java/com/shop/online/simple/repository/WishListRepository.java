package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.WishList;

public interface WishListRepository {

    WishList findByCustomer(Customer customer);

    void save(Product product, Customer customer);

    void delete(Product product, Customer customer);
}

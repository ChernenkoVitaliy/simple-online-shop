package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Cart;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CartRepository{

    Optional<Cart> findOne(long id);

    List<Cart> findAll();

    void save(Customer customer);

    void delete(Cart cart);

    void deleteProductFromCart(Cart cart, Product product);
}

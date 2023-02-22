package com.shop.online.simple.service;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Order;
import com.shop.online.simple.entity.Product;

import java.util.List;

public interface CustomerService {

    Product addToCart(Product product, Customer customer);

    void deleteProductFromCart(Product product, Customer customer);

    Order createOrder(Customer customer);

    List<Product> addProductToWishList(Product product, Customer customer);
}

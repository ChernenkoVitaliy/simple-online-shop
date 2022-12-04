package com.shop.online.simple.service;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Order;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.WishList;

public interface CustomerService {

    Product addToCart(Product product, Customer customer);

    boolean deleteProductFromCart(Product product, Customer customer);

    Order createOrder(Customer customer);

    WishList addProductToWishList(Product product, Customer customer);
}

package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Cart;
import com.shop.online.simple.entity.Product;

public interface CartRepository extends GeneralRepository<Cart>{

    boolean deleteProductFromCart(Product product);
}

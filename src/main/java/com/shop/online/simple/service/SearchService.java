package com.shop.online.simple.service;

import com.shop.online.simple.entity.Product;

import java.util.List;

public interface SearchService {

    List<Product> searchProductByName(String name);
}

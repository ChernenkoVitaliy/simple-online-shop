package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;

import java.util.List;

public interface ProductRepository extends GeneralRepository<Product> {

    List<Product> getProductsBySeller(Seller seller);

    List<Product> getProductsByTag(Tag tag);

}

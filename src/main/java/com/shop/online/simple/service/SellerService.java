package com.shop.online.simple.service;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;

public interface SellerService {

    Product addNewProduct(Product product, Seller seller);

    boolean deleteProduct(Product product, Seller seller);
}

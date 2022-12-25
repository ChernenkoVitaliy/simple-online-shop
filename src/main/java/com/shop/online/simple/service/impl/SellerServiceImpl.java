package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.service.SellerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SellerServiceImpl implements SellerService {
    private final transient ProductRepository productRepository;
    private final static String PRODUCT_ERR_TEXT = "Product must not be null.";
    private final static String SELLER_ERR_TEXT = "Seller must not be null.";

    public SellerServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*Seller should be able to add new products to sell.*/
    @Override
    public Product addNewProduct(final Product product, final Seller seller) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(seller, SELLER_ERR_TEXT);

        seller.getProducts().add(product);
        productRepository.save(product);

        return product;
    }

    /*Seller should be able to delete own products.*/
    @Override
    public boolean deleteProduct(final Product product, final Seller seller) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(seller, SELLER_ERR_TEXT);

        if (seller.getProducts().contains(product)) {
            productRepository.delete(product);

            return true;
        }

        return false;
    }
}

package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {
    private final transient ProductRepository productRepository;

    public SearchServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> searchProductByName(final String name) {
        Objects.requireNonNull(name, "Name of product must not be null.");

        return productRepository.findByNameContainsIgnoreCase(name);
    }
}

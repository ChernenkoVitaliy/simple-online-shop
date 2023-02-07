package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.repository.impl.ProductRepositoryImpl;
import com.shop.online.simple.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceImplTest {
    private ProductRepository productRepository = mock(ProductRepositoryImpl.class);
    private SearchService searchService;

    @BeforeEach
    public void setUp() {
        searchService = new SearchServiceImpl(productRepository);
    }

    @Test
    public void whenSearchProductByName_AndProductPrent_ThenReturnListOfProducts() {
        when(productRepository.findProductByName(any())).thenReturn(createProductsList());

        List<Product> result = searchService.searchProductByName("name");

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSearchProductByName_AndProductNotPrent_ThenReturnEmptyList() {
        when(productRepository.findProductByName(any())).thenReturn(Collections.emptyList());

        List<Product> result = searchService.searchProductByName("name");

        assertTrue(result.isEmpty());
    }

    private List<Product> createProductsList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product_1", "Product_1 description", BigDecimal.valueOf(10_000)));
        products.add(new Product("Product_2", "Product_2 description", BigDecimal.valueOf(20_000)));
        products.add(new Product("Product_3", "Product_3 description", BigDecimal.valueOf(30_000)));

        return products;
    }
}

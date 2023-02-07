package com.shop.online.simple.controller;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.dto.ProductDTO;
import com.shop.online.simple.entity.dto.mapper.ProductDTOMapper;
import com.shop.online.simple.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SearchService searchService;
    @MockBean
    private ProductDTOMapper productDTOMapper;

    @Test
    public void whenFormSent_thenShouldBeStatusOkAndContentPresent() throws Exception {
        when(searchService.searchProductByName(any())).thenReturn(createProductsList());
        when(productDTOMapper.toProductDTO(any())).thenReturn(createProductDTO());
        mockMvc.perform(post("/search")
                        .param("search-name", "name"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Overview")))
                .andExpect(content().string(containsString("Product name:")))
                .andExpect(content().string(containsString("Product description:")))
                .andExpect(content().string(containsString("Product price:")));
    }

    @Test
    public void whenFormSent_AndProductNotFound_ThenMessagePresent() throws Exception {
        mockMvc.perform(post("/search")
                        .param("search-name", "not exists product name"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("notFound"));
    }

    private List<Product> createProductsList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product_1", "Product_1 description", BigDecimal.valueOf(10_000)));

        return products;
    }

    private ProductDTO createProductDTO() {
        return new ProductDTO(1L, "Product_1", "Product_1 description", BigDecimal.valueOf(10_000));
    }
}

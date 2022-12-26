package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;
import com.shop.online.simple.entity.enums.AccountStatus;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import com.shop.online.simple.repository.rowmapper.TagRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class ProductRepositoryImplIntegrationTest {
    private Seller seller = createSeller();
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndProductPresentInDataBase_ThenTrue() {
        Optional<Product> result = productRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndProductsPresentInDataBase_ThenListNotEmpty() {
        List<Product> result = productRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveProduct_ThenProductPresentInDataBase() {
        Product product = new Product("Some new product",
                "Some product description",
                1111.11);

        productRepository.save(product, seller);

        Product result = jdbcTemplate.queryForObject("SELECT * FROM product WHERE product.name = ?", new ProductRowMapper(),
                product.getName());

        assertNotNull(result);
    }

    @Test
    public void whenUpdateProduct_ThenProductUpdatedInDataBase() {
        Product productForUpdating = productRepository.findOne(1L).get();
        productForUpdating.setName("New Product Name");

        productRepository.update(productForUpdating);
        Product updatedProduct = productRepository.findOne(1L).get();

        assertEquals(productForUpdating, updatedProduct);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteProduct_ThenProductDoNotPresentInDataBase() {
        Product productForDeleting = productRepository.findOne(1L).get();

        productRepository.delete(productForDeleting);
        Optional<Product> result = productRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenGetProductsBySeller_AndSuchProductPresentInDataBase_ThenListReturned() {
        List<Product> result = productRepository.findProductsBySeller(seller);

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenFindProductsByTag_AndSuchProductPresentInDataBase_ThenListReturned() {
        Tag tag = jdbcTemplate.query("SELECT * FROM tag WHERE id = ?", new TagRowMapper(), 1L).get(0);

        List<Product> result = productRepository.findProductsByTag(tag);

        assertTrue(result.size() > 0);
    }



    private Account createAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@mail.com");
        account.setPassword("paSSword");
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);

        return account;
    }

    private Seller createSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setAccount(createAccount());
        seller.setCompanyName("CompanyName");
        seller.setCompanyDescription("Some company description");
        seller.setPhones(new HashSet<>(List.of("111-11-11")));
        seller.setSite("company.com");

        return seller;
    }
}

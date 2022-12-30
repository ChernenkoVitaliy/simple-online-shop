package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.WishList;
import com.shop.online.simple.repository.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class WishListRepositoryImplIntegrationTest {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindByCustomer_AndWishListContainsProducts_ThenReturnWishListWithProducts() {
        final Customer customer = createCustomer();
        WishList result = wishListRepository.findByCustomer(customer);

        assertTrue(result.getProducts().size() > 0);
    }

    @Test
    public void whenSaveProduct_ThenProductPresentInDataBase() {
        Product newProduct = createProduct();
        Customer customer = createCustomer();
        wishListRepository.save(newProduct, customer);

        int result = jdbcTemplate.queryForObject(
                "SELECT customer_id FROM wish_list_product_customer WHERE product_id = ? AND customer_id = ?;",
                Integer.class, newProduct.getId(), customer.getId());

        assertTrue(result > 0);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteProductFromWishList_ThenProductNotPresentInDataBase() {
        Customer customer = createCustomer();
        WishList wishListFromDb = wishListRepository.findByCustomer(customer);
        Product productForDeleting = wishListFromDb.getProducts().get(0);
        wishListRepository.delete(productForDeleting, customer);

        wishListFromDb = wishListRepository.findByCustomer(customer);

        assertThat(wishListFromDb.getProducts()).doesNotContain(productForDeleting);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(2L);
        product.setName("product_name2");
        product.setDescription("product_description2");
        product.setPrice(2000.3);

        return product;
    }

    private Account createAccount() {
        Account account = new Account();
        account.setId(2L);
        account.setEmail("some@mail.com");
        account.setPassword("password1");
        account.setCreatedAt(LocalDateTime.now());

        return account;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setAccount(createAccount());
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.getCart().setId(1L);

        return customer;
    }
}

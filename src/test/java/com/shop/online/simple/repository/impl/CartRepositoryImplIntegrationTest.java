package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Cart;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartRepositoryImplIntegrationTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndCartPresentInDataBase_ThenReturnCart() {
        Optional<Cart> result = cartRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndCartsPresentInDataBase_ThenReturnListOfCarts() {
        List<Cart> result = cartRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveCart_ThenCartPresentInDataBase() {
        Customer customer = createCustomer();
        customer.getCart().getProducts().add(createProduct());

        cartRepository.save(customer);

        Cart result = jdbcTemplate.queryForObject("SELECT * FROM cart WHERE customer_id = ?",
                (rs, rowNum) -> {
            Cart cart = new Cart();
            cart.setId(rs.getLong("id"));
            return cart;
                }, customer.getId());

        assertTrue(result.getId() > 0);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteCart_ThenCartNotPresentInDataBase() {
        Cart cartForDeleting = cartRepository.findOne(1L).get();

        cartRepository.delete(cartForDeleting);
        Optional<Cart> result = cartRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteOneProductFromCart_ThenProductNotPresentInCart() {
        Cart cartForDb = cartRepository.findOne(1L).get();
        Product productForDeleting = cartForDb.getProducts().get(0);

        cartRepository.deleteProductFromCart(cartForDb, productForDeleting);
        cartForDb = cartRepository.findOne(1L).get();

        assertFalse(cartForDb.getProducts().contains(productForDeleting));
    }

    private Account createAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setEmail("some@mail.com");
        account.setPassword("password1");
        account.setCreatedAt(LocalDateTime.now());

        return account;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setAccount(createAccount());
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.getCart().setId(1L);

        return customer;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Some new product");
        product.setDescription("Description of Some new product.");

        return product;
    }
}

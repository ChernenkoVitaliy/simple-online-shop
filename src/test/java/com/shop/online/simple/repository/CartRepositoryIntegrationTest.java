package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Cart;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartRepositoryIntegrationTest {
    @Autowired
    private CartRepository cartRepository;

    @Test
    public void whenFindOne_AndCartPresentInDataBase_ThenReturnCart() {
        Optional<Cart> result = cartRepository.findById(1L);

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

        Cart result = cartRepository.save(customer.getCart());

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenDeleteCart_ThenCartNotPresentInDataBase() {
        Cart cartForDeleting = cartRepository.findById(1L).get();

        cartRepository.delete(cartForDeleting);
        Optional<Cart> result = cartRepository.findById(1L);

        assertTrue(result.isEmpty());
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
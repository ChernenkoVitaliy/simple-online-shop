package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.*;
import com.shop.online.simple.entity.enums.OrderStatus;
import com.shop.online.simple.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class OrderRepositoryImplIntegrationTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndOrderPresentInDataBase_ThenTrue() {
        Optional<Order> result = orderRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndOrdersPresentInDataBase_ThenListOfOrders() {
        List<Order> result = orderRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveOrder_ThenOrderPresentInDataBase() {
        Order newOrder = createOrder();
        newOrder.getProducts().add(createProduct());

        orderRepository.save(newOrder);

        Order result = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE customer_id = ? AND created_at = ?;",
                (rs, rowNum) -> {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    return order;
                }, newOrder.getCustomer().getId(), newOrder.getCreatedAt());

        assertTrue(result.getId() > 0);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteOrderFrom_ThenOrderNotPresentInDataBase() {
        Order orderForDeleting = orderRepository.findOne(1L).get();

        orderRepository.delete(orderForDeleting);
        Optional<Order> result = orderRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }



    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Some new product");
        product.setDescription("Description of Some new product.");

        return product;
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

    private Order createOrder() {
        Order order = new Order();
        order.setCustomer(createCustomer());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.NEW);

        return order;
    }
}

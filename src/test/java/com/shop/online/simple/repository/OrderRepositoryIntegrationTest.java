package com.shop.online.simple.repository;

import com.shop.online.simple.entity.*;
import com.shop.online.simple.entity.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class OrderRepositoryIntegrationTest {
    @Autowired
    private OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void whenFindOne_AndOrderPresentInDataBase_ThenTrue() {
        Optional<Order> result = orderRepository.findById(1L);

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

        Order result = orderRepository.save(newOrder);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenDeleteOrderFromDataBase_ThenOrderNotPresentInDataBase() {
        Order orderForDeleting = orderRepository.findById(1L).get();

        orderRepository.delete(orderForDeleting);
        Optional<Order> result = orderRepository.findById(1L);

        assertTrue(result.isEmpty());
    }

    private Product createProduct() {
        return (Product) entityManager.createQuery("SELECT p FROM Product p WHERE p.id = 1").getSingleResult();
    }

    private Customer createCustomer() {
        return (Customer) entityManager.createQuery("SELECT c FROM Customer c WHERE c.id = 1").getSingleResult();
    }

    private Order createOrder() {
        Order order = new Order();
        order.setCustomer(createCustomer());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.NEW);

        return order;
    }
}
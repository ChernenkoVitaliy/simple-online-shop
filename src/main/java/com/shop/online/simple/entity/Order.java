package com.shop.online.simple.entity;

import com.shop.online.simple.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private long id;
    private Customer customer;
    private LocalDateTime createdAt;
    private List<Product> products;
    private OrderStatus orderStatus;

    public Order() {
        this.products = new ArrayList<>();
    }

    public Order(Customer customer, List<Product> products, OrderStatus orderStatus) {
        this.customer = customer;
        this.createdAt = LocalDateTime.now();
        this.products = products;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.getId() && customer.equals(order.customer) && createdAt.equals(order.createdAt) && products.equals(order.products) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, createdAt, products, orderStatus);
    }
}

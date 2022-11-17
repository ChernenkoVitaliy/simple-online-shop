package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private Customer customer;
    private LocalDateTime created;
    private List<Product> products;
    private OrderStatus orderStatus;

    public Order(Customer customer, LocalDateTime created, List<Product> products, OrderStatus orderStatus) {
        this.customer = customer;
        this.created = created;
        this.products = products;
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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
        return customer.equals(order.customer) && created.equals(order.created) && products.equals(order.products) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, created, products, orderStatus);
    }
}

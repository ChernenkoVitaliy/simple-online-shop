package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Delivery {
    private Order order;
    private Customer customer;
    private LocalDateTime createdAt;
    private LocalDateTime deliveryDate;
    private LocalDateTime deliveredAt;

    public Delivery(Order order, Customer customer, LocalDateTime createdAt, LocalDateTime deliveryDate, LocalDateTime deliveredAt) {
        this.order = order;
        this.customer = customer;
        this.createdAt = createdAt;
        this.deliveryDate = deliveryDate;
        this.deliveredAt = deliveredAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return order.equals(delivery.order) && customer.equals(delivery.customer) && createdAt.equals(delivery.createdAt) && deliveryDate.equals(delivery.deliveryDate) && Objects.equals(deliveredAt, delivery.deliveredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, customer, createdAt, deliveryDate, deliveredAt);
    }
}

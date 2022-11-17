package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Feedback {
    private Customer customer;
    private String text;
    private LocalDateTime createdAt;

    public Feedback(Customer customer, String text, LocalDateTime createdAt) {
        this.customer = customer;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return customer.equals(feedback.customer) && text.equals(feedback.text) && createdAt.equals(feedback.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, text, createdAt);
    }
}

package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private LocalDateTime createdAt;
    private List<Product> products;

    public Cart() {
        this.createdAt = LocalDateTime.now();
        this.products = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(createdAt, cart.createdAt) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, products);
    }
}

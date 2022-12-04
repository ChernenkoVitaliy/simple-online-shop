package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private LocalDateTime created;
    private List<Product> products;

    public Cart() {
        this.created = LocalDateTime.now();
        this.products = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(created, cart.created) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, products);
    }
}

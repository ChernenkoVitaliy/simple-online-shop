package com.shop.online.simple.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private long id;
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == cart.getId() && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products);
    }
}

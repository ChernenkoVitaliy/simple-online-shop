package com.shop.online.simple.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishList {
    private List<Product> products;

    public WishList() {
        products = new ArrayList<>();
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
        WishList wishList = (WishList) o;
        return Objects.equals(products, wishList.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}

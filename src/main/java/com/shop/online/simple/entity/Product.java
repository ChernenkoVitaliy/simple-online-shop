package com.shop.online.simple.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Product {
    private long id;
    private String name;
    private String description;
    private Set<Tag> tags;
    private double price;
    private Set<Feedback> feedbacks;

    public Product() {
        this.tags = new HashSet<>();
        this.feedbacks = new HashSet<>();
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.tags = new HashSet<>();
        this.price = price;
        this.feedbacks = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && name.equals(product.name) && description.equals(product.description) && Objects.equals(tags, product.tags) && Objects.equals(feedbacks, product.feedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, tags, price, feedbacks);
    }
}

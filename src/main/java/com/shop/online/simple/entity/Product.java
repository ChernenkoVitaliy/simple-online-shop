package com.shop.online.simple.entity;

import java.util.Objects;
import java.util.Set;

public class Product {
    private String name;
    private String description;
    private Set<Tag> tags;
    private long price;
    private Set<Photo> photos;

    public Product(String name, String description, Set<Tag> tags, long price, Set<Photo> photos) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.photos = photos;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && name.equals(product.name) && description.equals(product.description) && Objects.equals(tags, product.tags) && Objects.equals(photos, product.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, tags, price, photos);
    }
}

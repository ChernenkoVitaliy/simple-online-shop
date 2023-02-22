package com.shop.online.simple.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_tags",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    public Product() {
        this.tags = new HashSet<>();
        this.feedbacks = new HashSet<>();
    }

    public Product(String name, String description, BigDecimal price) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(tags, product.tags) && Objects.equals(price, product.price) && Objects.equals(feedbacks, product.feedbacks) && Objects.equals(seller, product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, tags, price, feedbacks, seller);
    }
}

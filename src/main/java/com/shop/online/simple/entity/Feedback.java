package com.shop.online.simple.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Customer author;

    @Column(name = "feedback_text", nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    public Feedback() {}

    public Feedback(Customer author, String text, LocalDateTime createdAt) {
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id == feedback.id && author.equals(feedback.author) && text.equals(feedback.text) && createdAt.equals(feedback.createdAt) && product.equals(feedback.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, createdAt, product);
    }
}

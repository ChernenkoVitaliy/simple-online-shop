package com.shop.online.simple.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Feedback {
    private long id;
    private Customer author;
    private String text;
    private LocalDateTime createdAt;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id == feedback.id && author.equals(feedback.author) && text.equals(feedback.text) && createdAt.equals(feedback.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, createdAt);
    }
}

package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository{

    Optional<Feedback> findOne(long id);

    List<Feedback> findAll();

    void save(Feedback feedback, Product product);

    void delete(Feedback feedback);
}

package com.shop.online.simple.service;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;

public interface FeedbackService {

    Feedback addFeedback(Product product, Feedback feedback, Customer customer);
}

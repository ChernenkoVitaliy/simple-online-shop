package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.FeedbackRepository;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.service.FeedbackService;

import java.util.Objects;

public class FeedbackServiceImpl implements FeedbackService {
    private final transient FeedbackRepository feedbackRepo;
    private final transient ProductRepository productRepo;
    private final static String PRODUCT_ERR_TEXT = "Product must not be null.";
    private final static String AUTHOR_ERR_TEXT = "Author must not be null.";
    private final static String FEEDBACK_ERR_TEXT = "Feedback must not be null.";

    public FeedbackServiceImpl(final FeedbackRepository feedbackRepo, final ProductRepository productRepo) {
        this.feedbackRepo = feedbackRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Feedback addFeedback(final Product product, final Feedback feedback, final Customer author) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(feedback, FEEDBACK_ERR_TEXT);
        Objects.requireNonNull(author, AUTHOR_ERR_TEXT);

        feedback.setAuthor(author);
        product.getFeedbacks().add(feedback);


        productRepo.update(product);

        return feedbackRepo.save(feedback);
    }
}

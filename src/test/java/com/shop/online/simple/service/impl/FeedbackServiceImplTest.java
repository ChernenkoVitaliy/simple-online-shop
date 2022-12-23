package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.FeedbackRepository;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class FeedbackServiceImplTest {
    private FeedbackService feedbackService;
    private FeedbackRepository feedbackRepository = mock(FeedbackRepository.class);
    private ProductRepository productRepository = mock(ProductRepository.class);
    private Account account = createAccount();
    private Customer author = createCustomer();
    private Product product = createProduct();
    private Feedback feedback = new Feedback(author, "Some feedbacks text", LocalDateTime.now());
    private Feedback feedbackWithId;

    @BeforeEach
    public void setUp() {
        feedbackService = new FeedbackServiceImpl(feedbackRepository, productRepository);
        feedbackWithId = feedback;
    }

    @Test
    public void whenAddNewFeedback_ThenReturnFeedback() {

        var result = feedbackService.addFeedback(product, feedback, author);

        assertNotNull(result);
    }

    @Test
    public void whenAddNewFeedback_AndProductIsNull_ThenThrowException() {
        assertThatThrownBy(() -> feedbackService.addFeedback(null, feedback, author))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenAddNewFeedback_AndFeedbackIsNull_ThenThrowException() {
        assertThatThrownBy(() -> feedbackService.addFeedback(product, null, author))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenAddNewFeedback_AndAuthorIsNull_ThenThrowException() {
        assertThatThrownBy(() -> feedbackService.addFeedback(product, feedback, null))
                .isInstanceOf(NullPointerException.class);
    }

    private Product createProduct() {
        return new Product("Product name", "Some product description", 10_000L);
    }

    private Account createAccount() {
        return new Account("some_email@some.com", "passWord");
    }

    private Customer createCustomer() {
        return new Customer(account, "John", "Smith", "123-45-67");
    }

}

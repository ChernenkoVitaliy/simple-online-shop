package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.rowmapper.CustomerRowMapper;
import com.shop.online.simple.repository.rowmapper.FeedbackRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FeedbackRepositoryImplIntegrationTest {
    @Autowired
    private FeedbackRepositoryImpl feedbackRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndFeedbackPresentInDataBase_ThenTrue() {
        Optional<Feedback> result = feedbackRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndFeedbacksPresentInDataBase_ThenTrue() {
        List<Feedback> result = feedbackRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewFeedback_ThenFeedbackPresentInDataBase() {
        Product product = createProduct();
        Customer author = createAuthor();
        Feedback newFeedback = new Feedback(author, "Some feedback text", LocalDateTime.now());
        feedbackRepository.save(newFeedback, product);


        List<Feedback> result = jdbcTemplate.query("SELECT feedback.id, feedback.feedback_text, feedback.created_at, customer.name, customer.surname FROM feedback INNER JOIN customer ON customer.account_id = feedback.account_id WHERE feedback.feedback_text = ?;",
                new FeedbackRowMapper(), newFeedback.getText());

        assertEquals(result.get(0).getText(), newFeedback.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteFeedback_ThenFeedbackNotPresentInDataBase() {
        Feedback feedbackForDeleting = feedbackRepository.findOne(1L).get();

        feedbackRepository.delete(feedbackForDeleting);
        Optional<Feedback> result = feedbackRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }


    private Customer createAuthor() {
        final String SELECT_SQL = "SELECT * FROM customer " +
                "JOIN account ON customer.account_id = account.id " +
                "LEFT JOIN address ON customer.address_id = address.id " +
                "WHERE customer.id = ?";
        return jdbcTemplate.query(SELECT_SQL , new CustomerRowMapper(), 1L).get(0);
    }

    private Product createProduct() {
        final String SELECT_SQL = "SELECT product.id, product.name, product.description, product.price," +
                "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description," +
                "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at," +
                "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
                "FROM product " +
                "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
                "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
                "LEFT JOIN feedback ON product.id = feedback.product_id " +
                "LEFT JOIN customer ON feedback.account_id = customer.account_id " +
                "WHERE product.id = ?";

        return jdbcTemplate.query(SELECT_SQL, new ProductRowMapper(), 1L).get(0);
    }
}

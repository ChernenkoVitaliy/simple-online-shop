package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.FeedbackRepository;
import com.shop.online.simple.repository.rowmapper.FeedbackRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {
    private static final String SELECT_ONE_SQL = "SELECT feedback.id, feedback.feedback_text, feedback.created_at, customer.name, customer.surname FROM feedback INNER JOIN customer ON customer.account_id = feedback.account_id WHERE feedback.id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT feedback.id, feedback.feedback_text, feedback.created_at, customer.name, customer.surname FROM feedback INNER JOIN customer ON customer.account_id = feedback.account_id";
    private static final String INSERT_SQL = "INSERT INTO feedback(account_id, feedback_text, created_at, product_id) VALUES(?, ?, ?, ?);";
    private static final String DELETE_SQL = "DELETE FROM feedback WHERE id = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public FeedbackRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Feedback> findOne(final long id) {
        final List<Feedback> result = jdbcTemplate.query(SELECT_ONE_SQL, new FeedbackRowMapper(), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Feedback> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new FeedbackRowMapper());
    }

    @Override
    public void save(final Feedback feedback, final Product product) {
        jdbcTemplate.update(INSERT_SQL, feedback.getAuthor().getAccount().getId(), feedback.getText(),
                feedback.getCreatedAt(), product.getId());
    }

    @Override
    public void delete(final Feedback feedback) {
        jdbcTemplate.update(DELETE_SQL, feedback.getId());
    }
}

package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.repository.rowmapper.FeedbackRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import com.shop.online.simple.repository.rowmapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private static final String SELECT_ONE_SQL = "SELECT * FROM product WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM product;";
    private static final String TAGS_SQL = "SELECT tag.id, tag.name, tag.description FROM products_tags INNER JOIN tag ON products_tags.tag_id = tag.id WHERE product_id = ?;";
    private static final String FEEDBACKS_SQL = "SELECT feedback.id, feedback.feedback_text, feedback.created_at, customer.name, customer.surname FROM feedback INNER JOIN customer ON customer.account_id = feedback.account_id WHERE product_id = ?;";
    private static final String INSERT_SQL = "INSERT INTO product(name, description, price, seller_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE product SET name = ?, description = ?, price = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM product WHERE id = ?;";
    private static final String BY_SELLER_SQL = "SELECT * FROM product WHERE seller_id = ?;";
    private static final String BY_TAG_SQL = "SELECT product.id, product.name, product.description, product.price FROM products_tags INNER JOIN product ON products_tags.product_id = product.id WHERE tag_id = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> findOne(final long id) {
        final List<Product> result = jdbcTemplate.query(SELECT_ONE_SQL, new ProductRowMapper(), id);

        if (!result.isEmpty()) {
            final Product product = result.get(0);
            populateTagsAndFeedbacks(product);

            return Optional.of(product);
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        final List<Product> result = jdbcTemplate.query(SELECT_ALL_SQL, new ProductRowMapper());

        result.forEach(this::populateTagsAndFeedbacks);

        return result;
    }

    @Override
    public void save(final Product product, final Seller seller) {
        jdbcTemplate.update(INSERT_SQL, product.getName(), product.getDescription(), product.getPrice(), seller.getId());
    }

    @Override
    public void update(final Product product) {
        jdbcTemplate.update(UPDATE_SQL, product.getName(), product.getDescription(), product.getPrice(), product.getId());
    }

    @Override
    public void delete(final Product product) {
        jdbcTemplate.update(DELETE_SQL, product.getId());
    }

    @Override
    public List<Product> findProductsBySeller(final Seller seller) {
        return jdbcTemplate.query(BY_SELLER_SQL, new ProductRowMapper(), seller.getId());
    }

    @Override
    public List<Product> findProductsByTag(final Tag tag) {
        return jdbcTemplate.query(BY_TAG_SQL, new ProductRowMapper(), tag.getId());
    }

    private void populateTagsAndFeedbacks(final Product product) {
        final List<Tag> tags = jdbcTemplate.query(TAGS_SQL, new TagRowMapper(), product.getId());
        final List<Feedback> feedbacks = jdbcTemplate.query(FEEDBACKS_SQL, new FeedbackRowMapper(), product.getId());

        product.setTags(new HashSet<>(tags));
        product.setFeedbacks(new HashSet<>(feedbacks));
    }
}
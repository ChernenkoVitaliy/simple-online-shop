package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.Tag;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private static final String SELECT_SQL = "SELECT product.id, product.name, product.description, product.price," +
            "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description," +
            "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at," +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
            "FROM product " +
            "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
            "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
            "LEFT JOIN feedback ON product.id = feedback.product_id " +
            "LEFT JOIN customer ON feedback.account_id = customer.account_id ";
    private static final String INSERT_SQL = "INSERT INTO product(name, description, price, seller_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE product SET name = ?, description = ?, price = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM product WHERE id = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> findOne(final long id) {
        final List<Product> result = jdbcTemplate.query(SELECT_SQL + "WHERE product.id = ?;", new ProductRowMapper(), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_SQL, new ProductRowMapper());
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
        return jdbcTemplate.query(SELECT_SQL + "WHERE seller_id = ?;", new ProductRowMapper(), seller.getId());
    }

    @Override
    public List<Product> findProductsByTag(final Tag tag) {
        return jdbcTemplate.query(SELECT_SQL + "WHERE tag_id = ?;", new ProductRowMapper(), tag.getId());
    }

    @Override
    public List<Product> findProductByName(final String name) {
        return jdbcTemplate.query(SELECT_SQL + "WHERE LOWER(product.name) LIKE LOWER(?);", new ProductRowMapper(), "%" + name + "%");
    }
}
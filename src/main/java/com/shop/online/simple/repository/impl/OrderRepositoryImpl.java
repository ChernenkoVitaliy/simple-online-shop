package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Order;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.OrderRepository;
import com.shop.online.simple.repository.rowmapper.OrderRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders(created_at, order_status, customer_id) VALUES(?, ?, ?);";
    private static final String INSERT_PROD_SQL = "INSERT INTO orders_products(order_id, product_id) VALUES(?, ?);";
    private static final String DELETE_SQL = "DELETE FROM orders WHERE id = ?;";
    private static final String SELECT_SQL = "SELECT orders.id, orders.created_at, orders.order_status, " +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname, customer.phone as customer_phone, " +
            "address.id as address_id, address.country, address.city, address.street, " +
            "account.id as account_id, account.email FROM orders " +
            "JOIN customer ON orders.customer_id = customer.id " +
            "JOIN address ON customer.address_id = address.id " +
            "JOIN account ON account.id = customer.account_id ";
    private static final String SELECT_PROD_SQL = "SELECT product.id, product.name, product.description, product.price, " +
            "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description, " +
            "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at, " +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
            "FROM orders_products " +
            "LEFT JOIN orders ON orders_products.order_id = orders.id " +
            "LEFT JOIN product ON orders_products.product_id = product.id " +
            "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
            "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
            "LEFT JOIN feedback ON product.id = feedback.product_id " +
            "LEFT JOIN customer ON feedback.account_id = customer.account_id WHERE orders.id = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> findOne(final long id) {
        final List<Order> result = jdbcTemplate.query(SELECT_SQL + "WHERE orders.id = ?;", new OrderRowMapper(), id);

        if (!result.isEmpty()) {
            final Order order = result.get(0);
            populateOrder(order);

            return Optional.of(order);
        }

        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        final List<Order> orders = jdbcTemplate.query(SELECT_SQL, new OrderRowMapper());

        orders.forEach(this::populateOrder);

        return orders;
    }

    @Override
    public void save(final Order order) {
        jdbcTemplate.update(INSERT_ORDER_SQL, order.getCreatedAt(), order.getOrderStatus().toString().toUpperCase(Locale.US), order.getCustomer().getId());
        order.setId(jdbcTemplate.queryForObject("SELECT id FROM orders WHERE customer_id = ? AND created_at = ?;", Long.class, order.getCustomer().getId(), order.getCreatedAt()));
        order.getProducts().forEach(product -> jdbcTemplate.update(INSERT_PROD_SQL, order.getId(), product.getId()));
    }

    @Override
    public void delete(final Order order) {
        jdbcTemplate.update(DELETE_SQL, order.getId());
    }

    private void populateOrder(final Order order) {
        final List<Product> products = jdbcTemplate.query(SELECT_PROD_SQL, new ProductRowMapper(), order.getId());
        order.setProducts(products);
    }
}

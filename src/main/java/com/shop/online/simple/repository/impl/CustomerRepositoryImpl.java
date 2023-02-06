package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.repository.rowmapper.CustomerRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final String INSERT_SQL = "INSERT INTO customer(account_id, name, surname, phone) VALUES(?, ?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE customer SET name = ?, surname = ?, phone =? WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM customer " +
            "JOIN account ON customer.account_id = account.id " +
            "LEFT JOIN address ON customer.address_id = address.id ";
    private static final String SELECT_PROD_SQL = "SELECT product.id, product.name, product.description, product.price, " +
            "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description, " +
            "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at, " +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
            "FROM wish_list_product_customer " +
            "LEFT JOIN product ON wish_list_product_customer.product_id = product.id " +
            "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
            "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
            "LEFT JOIN feedback ON product.id = feedback.product_id " +
            "LEFT JOIN customer ON feedback.account_id = customer.account_id " +
            "WHERE wish_list_product_customer.customer_id  = ?;";
    private transient final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> findOne(final long id) {
        final List<Customer> result = jdbcTemplate.query(SELECT_SQL + "WHERE customer.id = ?;", new CustomerRowMapper(), id);

        if (!result.isEmpty()) {
            final Customer customer = populateProductsToCustomer(result);

            return Optional.of(customer);
        }

        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_SQL,  new CustomerRowMapper());
    }

    @Override
    public void save(final Customer customer) {
        jdbcTemplate.update(INSERT_SQL, customer.getAccount().getId(), customer.getName(), customer.getSurname(), customer.getPhone());
    }

    @Override
    public void update(final Customer customer) {
        jdbcTemplate.update(UPDATE_SQL, customer.getName(), customer.getSurname(), customer.getPhone(), customer.getId());
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        final List<Customer> result = jdbcTemplate.query(SELECT_SQL + "WHERE account.email = ?;", new CustomerRowMapper(), email);

        if (!result.isEmpty()) {
            final Customer customer = populateProductsToCustomer(result);

            return Optional.of(customer);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByPhone(final String phone) {
        final List<Customer> result = jdbcTemplate.query(SELECT_SQL + "WHERE customer.phone = ?;", new CustomerRowMapper(), phone);

        if (!result.isEmpty()) {
            final Customer customer = populateProductsToCustomer(result);

            return Optional.of(customer);
        }

        return Optional.empty();
    }

    private Customer populateProductsToCustomer(final List<Customer> result) {
        final Customer customer = result.get(0);
        final List<Product> products = jdbcTemplate.query(SELECT_PROD_SQL, new ProductRowMapper(), customer.getId());
        customer.getWishList().setProducts(products);
        return customer;
    }
}

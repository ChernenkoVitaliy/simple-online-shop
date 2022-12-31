package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.WishList;
import com.shop.online.simple.repository.WishListRepository;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepositoryImpl implements WishListRepository {
    private static final String INSERT_SQL = "INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(?, ?);";
    private static final String DELETE_SQL = "DELETE FROM wish_list_product_customer WHERE product_id = ?;";
    private static final String SELECT_ONE_SQL = "SELECT product.id, product.name, product.description, product.price, " +
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

    public WishListRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WishList findByCustomer(final Customer customer) {
        final List<Product> products = jdbcTemplate.query(SELECT_ONE_SQL, new ProductRowMapper(), customer.getId());
        customer.getWishList().setProducts(products);

        return customer.getWishList();
    }

    @Override
    public void save(final Product product, final Customer customer) {
        jdbcTemplate.update(INSERT_SQL, product.getId(), customer.getId());
    }

    @Override
    public void delete(final Product product, final Customer customer) {
        jdbcTemplate.update(DELETE_SQL, product.getId());
    }
}

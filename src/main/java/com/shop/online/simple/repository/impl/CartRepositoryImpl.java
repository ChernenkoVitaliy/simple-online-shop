package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Cart;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.repository.CartRepository;
import com.shop.online.simple.repository.rowmapper.CartRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private static final String SELECT_ONE_SQL = "SELECT * FROM cart WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM cart;";
    private static final String INSERT_PROD_SQL = "INSERT INTO carts_products(cart_id, product_id) VALUES(?, ?);";
    private static final String INSERT_CART_SQL = "INSERT INTO cart (customer_id) VALUES (?);";
    private static final String DELETE_SQL = "DELETE FROM cart WHERE id = ?;";
    private static final String DELETE_ONE_SQL = "DELETE FROM carts_products WHERE cart_id = ? AND product_id = ?;";
    private static final String SELECT_PROD_SQL = "SELECT product.id, product.name, product.description, product.price, " +
            "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description, " +
            "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at, " +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
            "FROM carts_products " +
            "LEFT JOIN cart ON carts_products.cart_id = cart.id " +
            "LEFT JOIN product ON carts_products.product_id = product.id " +
            "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
            "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
            "LEFT JOIN feedback ON product.id = feedback.product_id " +
            "LEFT JOIN customer ON feedback.account_id = customer.account_id " +
            "WHERE cart.id = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public CartRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Cart> findOne(final long id) {
        final List<Cart> result = jdbcTemplate.query(SELECT_ONE_SQL, new CartRowMapper(), id);

        if (!result.isEmpty()) {
            final Cart cart = result.get(0);
            populateCart(cart);

            return Optional.of(cart);
        }
        return Optional.empty();
    }

    @Override
    public List<Cart> findAll() {
        final List<Cart> result = jdbcTemplate.query(SELECT_ALL_SQL, new CartRowMapper());

        result.forEach(this::populateCart);

        return result;
    }

    @Override
    public void save(final Customer customer) {
        jdbcTemplate.update(INSERT_CART_SQL, customer.getId());
        customer.getCart().getProducts().forEach(product -> jdbcTemplate.update(INSERT_PROD_SQL, customer.getCart().getId(), product.getId()));
    }

    @Override
    public void delete(final Cart cart) {
        jdbcTemplate.update(DELETE_SQL, cart.getId());
    }

    @Override
    public void deleteProductFromCart(final Cart cart, final Product product) {
        jdbcTemplate.update(DELETE_ONE_SQL, cart. getId(), product.getId());
    }

    private void populateCart(final Cart cart) {
        final List<Product> products = jdbcTemplate.query(SELECT_PROD_SQL, new ProductRowMapper(), cart.getId());
        cart.setProducts(products);
    }
}

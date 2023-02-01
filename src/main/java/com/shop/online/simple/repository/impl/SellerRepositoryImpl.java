package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.repository.SellerRepository;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import com.shop.online.simple.repository.rowmapper.SellerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class SellerRepositoryImpl implements SellerRepository {
    private static final String SELECT_SQL = "SELECT seller.id as seller_id, company_name, company_description, company_site, " +
            "account.id as account_id, email, password, created_at, account_status " +
            "FROM seller " +
            "INNER JOIN account ON seller.account_id = account.id ";
    private static final String SELECT_PROD_SQL = "SELECT product.id, product.name, product.description, product.price, " +
            "tag.id as tag_id, tag.name as tag_name, tag.description as tag_description, " +
            "feedback.id as feedback_id, feedback.feedback_text, feedback.created_at, " +
            "customer.id as customer_id, customer.name as customer_name, customer.surname as customer_surname " +
            "FROM product " +
            "LEFT JOIN products_tags ON product.id = products_tags.product_id " +
            "LEFT JOIN tag ON products_tags.tag_id = tag.id " +
            "LEFT JOIN feedback ON product.id = feedback.product_id " +
            "LEFT JOIN customer ON feedback.account_id = customer.account_id WHERE seller_id = ?;";
    private static final String SELECT_PHONES_SQL = "SELECT * FROM seller_phones WHERE seller_id = ?;";
    private static final String INSERT_SELLER_SQL = "INSERT INTO seller(account_id, company_name, company_description, company_site) VALUES (?, ?, ?, ?);";
    private static final String INSERT_PHONE_SQL = "INSERT INTO seller_phones(phone, seller_id) VALUES(?, ?);";
    private static final String UPDATE_SQL = "UPDATE seller SET company_name = ?, company_description = ?, company_site = ? WHERE id = ?";

    private transient final JdbcTemplate jdbcTemplate;

    public SellerRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Seller> findOne(final long id) {
        final List<Seller> result = jdbcTemplate.query(SELECT_SQL + "WHERE seller.id = ?;", new SellerRowMapper(), id);

        if (!result.isEmpty()) {
            final Seller seller = result.get(0);
            populateSeller(seller);

            return Optional.of(seller);
        }

        return Optional.empty();
    }

    @Override
    public List<Seller> findAll() {
        return jdbcTemplate.query(SELECT_SQL, new SellerRowMapper());
    }

    @Override
    @Transactional
    public void save(final Seller seller) {
        jdbcTemplate.update(INSERT_SELLER_SQL, seller.getAccount().getId(), seller.getCompanyName(), seller.getCompanyDescription(), seller.getSite());
        seller.setId(jdbcTemplate.queryForObject("SELECT id FROM seller WHERE account_id = ?;", Long.class, seller.getAccount().getId()));
        jdbcTemplate.batchUpdate(INSERT_PHONE_SQL, seller.getPhones(),
                10,
                (PreparedStatement ps, String phone) -> {
                ps.setString(1, phone);
                ps.setLong(2, seller.getId());
                });
    }

    @Override
    public void update(final Seller seller) {
        jdbcTemplate.update(UPDATE_SQL, seller.getCompanyName(), seller.getCompanyDescription(), seller.getSite(), seller.getId());
    }

    private void populateSeller(final Seller seller) {
        final List<Product> products = jdbcTemplate.query(SELECT_PROD_SQL, new ProductRowMapper(), seller.getId());
        final List<String> phones = jdbcTemplate.query(SELECT_PHONES_SQL, (rs, rowNum) -> rs.getString("phone"), seller.getId());
        seller.setProducts(products);
        seller.setPhones(new HashSet<>(phones));
    }
}

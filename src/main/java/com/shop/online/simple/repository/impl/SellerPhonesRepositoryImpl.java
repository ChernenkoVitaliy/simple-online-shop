package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.repository.SellerPhonesRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SellerPhonesRepositoryImpl implements SellerPhonesRepository {
    private static final String SELECT_ALL_SQL = "SELECT seller_phones.phone FROM seller_phones;";
    private static final String INSERT_SQL = "INSERT INTO seller_phones(phone, seller_id) VALUES(?, ?);";
    private static final String DELETE_SQL = "DELETE FROM seller_phones WHERE phone = ?;";
    private static final String SELECT_SELLER_SQL = "SELECT * FROM seller_phones WHERE seller_id = ?;";
    private static final String PHONE_COLUMN = "phone";
    private transient final JdbcTemplate jdbcTemplate;

    public SellerPhonesRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, (rs, rowNum) -> rs.getString(PHONE_COLUMN));
    }

    @Override
    public void save(final String phone, final Seller seller) {
        jdbcTemplate.update(INSERT_SQL, phone, seller.getId());
    }

    @Override
    public void delete(final String number) {
        jdbcTemplate.update(DELETE_SQL, number);
    }

    @Override
    public List<String> findBySellerId(final long id) {
        return jdbcTemplate.query(SELECT_SELLER_SQL, (rs, rowNum) -> rs.getString(PHONE_COLUMN), id);
    }
}

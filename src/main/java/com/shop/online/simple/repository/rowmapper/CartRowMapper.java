package com.shop.online.simple.repository.rowmapper;


import com.shop.online.simple.entity.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Cart cart = new Cart();
        cart.setId(rs.getLong("id"));

        return cart;
    }
}

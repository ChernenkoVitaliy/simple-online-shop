package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));

        return address;
    }
}

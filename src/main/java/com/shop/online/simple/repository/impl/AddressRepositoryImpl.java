package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Address;
import com.shop.online.simple.repository.AddressRepository;
import com.shop.online.simple.repository.rowmapper.AddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private static final String INSERT_SQL = "INSERT INTO address(country, city, street) VALUES(?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE address SET country = ?, city = ?, street = ? WHERE id = ?;";
    private static final String SELECT_ONE_SQL = "SELECT * FROM address WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM address;";
    private static final String DELETE_ONE_SQL = "DELETE FROM address WHERE id = ?";
    private transient final JdbcTemplate jdbcTemplate;

    public AddressRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Address> findOne(final long id) {
        final List<Address> result = jdbcTemplate.query(SELECT_ONE_SQL, new AddressRowMapper(), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Address> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new AddressRowMapper());
    }

    @Override
    public void save(final Address address) {
        jdbcTemplate.update(INSERT_SQL, address.getCountry(), address.getCity(), address.getStreet());
    }

    @Override
    public void update(final Address address) {
        jdbcTemplate.update(UPDATE_SQL,
                address.getCountry(), address.getCity(), address.getStreet(),
                address.getId());
    }

    @Override
    public void delete(final Address address) {
        jdbcTemplate.update(DELETE_ONE_SQL, address.getId());
    }
}

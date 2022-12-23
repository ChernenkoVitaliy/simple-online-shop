package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.rowmapper.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final String INSERT_SQL = "INSERT INTO account(email, password, created_at, account_status) VALUES(?, ?, ?, ?);";
    private final String UPDATE_SQL = "UPDATE account SET email = ?, password = ?, account_status = ? WHERE id = ?;";
    private final String SELECT_ONE_SQL = "SELECT * FROM account WHERE id = ?;";
    private final String SELECT_ALL_SQL = "SELECT * FROM account;";
    private final String DELETE_ONE_SQL = "DELETE FROM account WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    public AccountRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> findOne(final long id) {
        List<Account> result = jdbcTemplate.query(SELECT_ONE_SQL, new AccountRowMapper(), id);

        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new AccountRowMapper());
    }

    @Override
    public void save(final Account account) {
        jdbcTemplate.update(INSERT_SQL,
                account.getEmail(), account.getPassword(), account.getCreatedAt(), account.getAccountStatus().toString().toUpperCase());
    }

    @Override
    public void update(final Account account) {
        jdbcTemplate.update(UPDATE_SQL,
                account.getEmail(), account.getPassword(), account.getAccountStatus().toString().toUpperCase(),
                account.getId());
    }

    @Override
    public void delete(final Account account) {
        jdbcTemplate.update(DELETE_ONE_SQL, account.getId());
    }
}

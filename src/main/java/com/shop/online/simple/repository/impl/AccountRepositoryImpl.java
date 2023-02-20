package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.rowmapper.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private static final String INSERT_SQL = "INSERT INTO account(email, password, created_at, account_status) VALUES(?, ?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE account SET email = ?, password = ?, account_status = ? WHERE id = ?;";
    private static final String SELECT_ONE_SQL = "SELECT * FROM account WHERE id = ?;";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM account WHERE email = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM account;";
    private static final String DELETE_ONE_SQL = "DELETE FROM account WHERE id = ?";
    private transient final JdbcTemplate jdbcTemplate;

    public AccountRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> findOne(final long id) {
        final List<Account> result = jdbcTemplate.query(SELECT_ONE_SQL, new AccountRowMapper(), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new AccountRowMapper());
    }

    @Override
    public void save(final Account account) {
        jdbcTemplate.update(INSERT_SQL,
                account.getEmail(), account.getPassword(), account.getCreatedAt(), account.getAccountStatus().toString().toUpperCase(Locale.US));
    }

    @Override
    public void update(final Account account) {
        jdbcTemplate.update(UPDATE_SQL,
                account.getEmail(), account.getPassword(), account.getAccountStatus().toString().toUpperCase(Locale.US),
                account.getId());
    }

    @Override
    public void delete(final Account account) {
        jdbcTemplate.update(DELETE_ONE_SQL, account.getId());
    }

    @Override
    public Optional<Account> findByEmail(final String email) {
        final List<Account> result = jdbcTemplate.query(SELECT_BY_EMAIL_SQL, new AccountRowMapper(), email);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}

package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.enums.AccountStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));

        return account;
    }
}

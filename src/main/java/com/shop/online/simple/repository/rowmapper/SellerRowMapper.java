package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.enums.AccountStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerRowMapper implements RowMapper<Seller> {

    @Override
    public Seller mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Account account = new Account();
        final Seller seller = new Seller();

        populateAccount(rs, account);
        populateSeller(rs, seller, account);

        return seller;
    }

    private void populateAccount(final ResultSet rs, final Account account) throws SQLException {
        account.setId(rs.getLong("account_id"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
    }

    private void populateSeller(final ResultSet rs, final Seller seller, final Account account) throws SQLException {
        seller.setId(rs.getLong("seller_id"));
        seller.setCompanyName(rs.getString("company_name"));
        seller.setCompanyDescription(rs.getString("company_description"));
        seller.setSite(rs.getString("company_site"));
        seller.setAccount(account);
    }
}

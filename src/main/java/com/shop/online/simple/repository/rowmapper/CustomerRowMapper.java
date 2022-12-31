package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Address;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.enums.AccountStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Account account = new Account();
        final Address address = new Address();
        final Customer customer = new Customer();

        populateAccount(rs, account);
        populateAddress(rs, address);
        populateCustomer(rs, customer, account, address);

        return customer;
    }

    private void populateAccount(final ResultSet rs, final Account account) throws SQLException {
        account.setId(rs.getLong("account.id"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    }

    private void populateAddress(final ResultSet rs, final Address address) throws SQLException {
        address.setId(rs.getLong("address.id"));
        if (address.getId() > 0) {
            address.setCountry(rs.getString("country"));
            address.setCity(rs.getString("city"));
            address.setStreet(rs.getString("street"));
        }
    }

    private void populateCustomer(final ResultSet rs, final Customer customer, final Account account, final Address address) throws SQLException {
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setPhone(rs.getString("phone"));
        customer.setAccount(account);
        customer.setDeliveryAddress(address);
    }

}

package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Address;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Order;
import com.shop.online.simple.entity.enums.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Order order = new Order();
        final Customer customer = new Customer();
        final Address address = new Address();
        final Account account = new Account();

        populateAccount(rs, account);
        populateAddress(rs, address);
        populateCustomer(rs, customer,account, address);
        populateOrder(rs, order, customer);

        return order;
    }

    private void populateOrder(final ResultSet rs, final Order order, final Customer customer) throws SQLException {
        order.setId(rs.getLong("id"));
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        order.setCustomer(customer);
    }

    private void populateCustomer(final ResultSet rs, final Customer customer, final Account account, final Address address) throws SQLException {
        customer.setId(rs.getLong("customer_id"));
        customer.setName(rs.getString("customer_name"));
        customer.setSurname(rs.getString("customer_surname"));
        customer.setPhone(rs.getString("customer_phone"));
        customer.setAccount(account);
        customer.setDeliveryAddress(address);
    }

    private void populateAddress(final ResultSet rs, final Address address) throws SQLException {
        address.setId(rs.getLong("address_id"));
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
    }

    private void populateAccount(final ResultSet rs, final Account account) throws SQLException {
        account.setId(rs.getLong("account_id"));
        account.setEmail(rs.getString("email"));
    }
}

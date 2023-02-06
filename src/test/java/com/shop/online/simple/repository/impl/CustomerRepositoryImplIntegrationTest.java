package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.enums.AccountStatus;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.repository.rowmapper.AccountRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryImplIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndCustomerPresentInDataBase_ThenReturnCustomer() {
        Optional<Customer> result = customerRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindOne_AndCustomerNotPresentInDataBase_ThenReturnEmptyOptional() {
        Optional<Customer> result = customerRepository.findOne(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindAll_AndCustomersPresentInDataBase_ThenReturnListOfCustomers() {
        List<Customer> result = customerRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewCustomer_ThenCustomerPresentInDataBase() {
        Customer newCustomer = createCustomer();
        jdbcTemplate.update("INSERT INTO account(email, password, created_at, account_status) VALUES(?, ?, ?, ?);",
                newCustomer.getAccount().getEmail(), newCustomer.getAccount().getPassword(),
                newCustomer.getAccount().getCreatedAt(), newCustomer.getAccount().getAccountStatus().toString().toUpperCase());
        Account account = jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ?",
                new AccountRowMapper(), newCustomer.getAccount().getEmail());
        newCustomer.setAccount(account);

        customerRepository.save(newCustomer);
        Customer customerFromDb = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE account_id = ?",
                (rs, rowNum) -> {
            Customer c = new Customer();
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setSurname(rs.getString("surname"));
            c.setPhone(rs.getString("phone"));
            return c;
                }, account.getId());

        assertNotNull(customerFromDb);
    }

    @Test
    @Transactional
    @Rollback
    public void whenUpdateCustomer_ThenCustomerUpdatedInDataBase() {
        Customer customerForUpdating = customerRepository.findOne(1L).get();
        customerForUpdating.setPhone("111-22-33");

        customerRepository.update(customerForUpdating);
        Customer updatedCustomer = customerRepository.findOne(1L).get();

        assertEquals(customerForUpdating, updatedCustomer);
    }

    @Test
    public void whenFindByEmail_AndCustomerPresentInDataBase_ThenReturnCustomer() {
        Optional<Customer> result = customerRepository.findByEmail("email1@mail.com");

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindByEmail_AndCustomerNotPresentInDataBase_ThenReturnEmptyOptional() {
        Optional<Customer> result = customerRepository.findByEmail("not_exists_email@mail.com");

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindByPhone_AndCustomerPresentInDataBase_ThenReturnCustomer() {
        Optional<Customer> result = customerRepository.findByPhone("012-345-67");

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindByPhone_AndCustomerNotPresentInDataBase_ThenReturnEmptyOptional() {
        Optional<Customer> result = customerRepository.findByPhone("000-000-00-00");

        assertTrue(result.isEmpty());
    }

    private Account createAccount() {
        Account account = new Account();
        account.setEmail("someNewEmail@mail.com");
        account.setPassword("SomeNewPassword1");
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);

        return account;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setAccount(createAccount());
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setPhone("000-11-22");
        customer.getCart().setId(1L);

        return customer;
    }
}

package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Account;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.rowmapper.AccountRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class AccountRepositoryImplIntegrationTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndAccountPresentInDataBase_ThenTrue() {
        Optional<Account> result = accountRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndAccountsPresentInDataBase_ThenTrue() {
        List<Account> result = accountRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewAccount_ThenAccountPresentInDataBase() {
        Account newAccount = new Account("newTestAccount@some.com", "newAccountPAssword");
        accountRepository.save(newAccount);

        List<Account> result = jdbcTemplate.query("SELECT * FROM account WHERE email = ?",
                new AccountRowMapper(), newAccount.getEmail());

        assertEquals(result.get(0).getEmail(), newAccount.getEmail());
    }

    @Test
    public void whenUpdateAccount_ThenAccountUpdatesInDataBase() {
        Account accountForUpdating = accountRepository.findOne(1L).get();
        accountForUpdating.setPassword("newUpdatedPassword");

        accountRepository.update(accountForUpdating);
        Account updatedAccount = accountRepository.findOne(1L).get();

        assertEquals(accountForUpdating, updatedAccount);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteAccount_ThenAccountNotPresentInDataBase() {
        Account accountForDeleting = accountRepository.findOne(1L).get();

        accountRepository.delete(accountForDeleting);
        Optional<Account> result = accountRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }
}

package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountRepositoryIntegrationTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void whenFindOneByID_AndAccountPresentInDataBase_ThenTrue() {
        Optional<Account> result = accountRepository.findById(1L);

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

        Account result = accountRepository.save(newAccount);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenUpdateAccount_ThenAccountUpdatesInDataBase() {
        Account accountForUpdating = accountRepository.findById(1L).get();
        accountForUpdating.setPassword("newUpdatedPassword");

        accountRepository.save(accountForUpdating);
        Account updatedAccount = accountRepository.findById(1L).get();

        assertEquals(accountForUpdating, updatedAccount);
    }

    @Test
    public void whenDeleteAccount_ThenAccountNotPresentInDataBase() {
        Account accountForDeleting = accountRepository.findById(1L).get();

        accountRepository.delete(accountForDeleting);
        Optional<Account> result = accountRepository.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindOneByEmail_AndAccountPresentInDataBase_ThenTrue() {
        Optional<Account> result = accountRepository.findByEmail("email1@mail.com");

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindOneByEmail_AndAccountNotPresentInDataBase_ThenReturnEmptyOptional() {
        Optional<Account> result = accountRepository.findByEmail("not_exists_email@mail.com");

        assertTrue(result.isEmpty());
    }
}
package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.repository.impl.AccountRepositoryImpl;
import com.shop.online.simple.repository.impl.CustomerRepositoryImpl;
import com.shop.online.simple.service.RegistrationCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistrationCheckServiceImplTest {
    private RegistrationCheckService registrationCheckService;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private Account accountWithId = createAccount();
    private Customer customerWithId = createCustomer();

    @BeforeEach
    public void setUp() {
        accountRepository = mock(AccountRepositoryImpl.class);
        customerRepository = mock(CustomerRepositoryImpl.class);
        registrationCheckService = new RegistrationCheckServiceImpl(accountRepository, customerRepository);
    }

    @Test
    public void isEmailExists_ShouldReturnTrueIfEmailPresent() {
        when(accountRepository.findByEmail(any())).thenReturn(Optional.of(accountWithId));

        boolean result = registrationCheckService.isEmailExists(accountWithId.getEmail());

        assertTrue(result);
    }

    @Test
    public void isCustomerPhoneExists_ShouldReturnTrueIfPhonePresent() {
        when(customerRepository.findByPhone(any())).thenReturn(Optional.of(customerWithId));

        boolean result = registrationCheckService.isCustomerPhoneExists(customerWithId.getPhone());

        assertTrue(result);
    }



    private Account createAccount() {
        Account account = new Account("some_email@some.com", "passWord");
        account.setId(1L);

        return account;
    }

    private Customer createCustomer() {
        Customer customer = new Customer(accountWithId, "John", "Smith", "123-45-67");
        customer.setId(1L);

        return customer;
    }
}

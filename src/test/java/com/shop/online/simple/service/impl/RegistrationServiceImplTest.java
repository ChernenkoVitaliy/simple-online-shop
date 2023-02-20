package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.repository.impl.AccountRepositoryImpl;
import com.shop.online.simple.repository.impl.CustomerRepositoryImpl;
import com.shop.online.simple.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private AccountRepository accountRepository = mock(AccountRepositoryImpl.class);
    private CustomerRepository customerRepository = mock(CustomerRepositoryImpl.class);
    private Account account = createAccount();
    private Customer customer;
    private Customer customerWithId;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl(accountRepository, customerRepository);
        customerWithId = createCustomer();
        customerWithId.setId(1L);
    }

    @Test
    public void whenCreateNewCustomer_ThenReturnCustomerWithId() {
        when(accountRepository.findByEmail(any())).thenReturn(Optional.of(account));
        when(customerRepository.findByEmail(any())).thenReturn(Optional.of(customerWithId));
        customer = createCustomer();

        Customer result = registrationService.createNewCustomer(customer);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void createNewCustomerMethod_ShouldThrowException_WhenCustomerIsNull() {
        assertThatThrownBy(() -> registrationService.createNewCustomer(null))
                .isInstanceOf(NullPointerException.class);
    }

    private Account createAccount() {
        return new Account("some_email@some.com", "passWord");
    }

    private Customer createCustomer() {
        return new Customer(account, "John", "Smith", "123-45-67");
    }

}

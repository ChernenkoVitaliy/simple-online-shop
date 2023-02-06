package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.service.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final transient AccountRepository accountRepository;
    private final transient CustomerRepository customerRepository;
    private final static String CUSTOMER_ERR_TEXT = "Customer must not be null.";


    public RegistrationServiceImpl(final AccountRepository accountRepository, final CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createNewCustomer(final Customer customer) {
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        accountRepository.save(customer.getAccount());
        final Account account = accountRepository.findByEmail(customer.getAccount().getEmail()).get();
        customer.setAccount(account);
        customerRepository.save(customer);

        return customerRepository.findByEmail(customer.getAccount().getEmail()).get();
    }
}

package com.shop.online.simple.service.impl;

import com.shop.online.simple.repository.AccountRepository;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.service.RegistrationCheckService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationCheckServiceImpl implements RegistrationCheckService {
    private final transient AccountRepository accountRepository;
    private final transient CustomerRepository customerRepository;

    public RegistrationCheckServiceImpl(final AccountRepository accountRepository, final CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isEmailExists(final String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isCustomerPhoneExists(final String phone) {
        return customerRepository.findByPhone(phone).isPresent();
    }
}

package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.service.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final transient CustomerRepository customerRepository;
    private final static String CUSTOMER_ERR_TEXT = "Customer must not be null.";

    public RegistrationServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createNewCustomer(final Customer customer) {
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        return customerRepository.save(customer);
    }
}

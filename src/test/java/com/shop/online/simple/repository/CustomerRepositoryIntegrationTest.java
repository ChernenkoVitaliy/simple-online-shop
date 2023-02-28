package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindOne_AndCustomerPresentInDataBase_ThenReturnCustomer() {
        Optional<Customer> result = customerRepository.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindOne_AndCustomerNotPresentInDataBase_ThenReturnEmptyOptional() {
        Optional<Customer> result = customerRepository.findById(999L);

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
        Account newAccount = createAccount();
        newCustomer.setAccount(newAccount);

        Customer result = customerRepository.save(newCustomer);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenUpdateCustomer_ThenCustomerUpdatedInDataBase() {
        Customer customerForUpdating = customerRepository.findById(1L).get();
        customerForUpdating.setPhone("111-22-33");

        customerRepository.save(customerForUpdating);
        Customer updatedCustomer = customerRepository.findById(1L).get();

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

        return customer;
    }
}
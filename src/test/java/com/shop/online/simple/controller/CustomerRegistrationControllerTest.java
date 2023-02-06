package com.shop.online.simple.controller;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.dto.AccountDTO;
import com.shop.online.simple.entity.dto.CustomerDTO;
import com.shop.online.simple.entity.dto.mapper.CustomerDTOMapper;
import com.shop.online.simple.entity.enums.AccountStatus;
import com.shop.online.simple.service.RegistrationCheckService;
import com.shop.online.simple.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRegistrationController.class)
public class CustomerRegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private CustomerDTOMapper customerDTOMapper;
    @MockBean
    private RegistrationCheckService registrationCheckService;

    @Test
    public void showRegistrationPage_ShouldReturnPageWithRegistrationForm() throws Exception {
        mockMvc.perform(get("/customer/registration"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Please fill in this form to create an account")))
                .andExpect(content().string(containsString("Name")))
                .andExpect(content().string(containsString("Surname")))
                .andExpect(content().string(containsString("Phone")))
                .andExpect(content().string(containsString("Email")))
                .andExpect(content().string(containsString("Password")));
    }

    @Test
    public void whenCorrectRegistrationFormSent_thenShouldBeStatusOkAndContentPresent() throws Exception {
        Customer customerWithId = createCustomer();
        customerWithId.setId(100L);
        CustomerDTO customerDTO = createCustomerDTO();
        when(registrationCheckService.isEmailExists(any())).thenReturn(false);
        when(registrationCheckService.isCustomerPhoneExists(any())).thenReturn(false);
        when(registrationService.createNewCustomer(any())).thenReturn(customerWithId);
        when(customerDTOMapper.toCustomerDTO(any())).thenReturn(customerDTO);

        mockMvc.perform(post("/customer/registration")
                .param("name", "John")
                .param("surname", "Smith")
                .param("phone", "1112223344")
                .param("email", "some_new_email@mail.com")
                .param("password", "Helk34fw_wreJ4"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Customer info")))
                .andExpect(content().string(containsString("Customer ID:")))
                .andExpect(content().string(containsString("Customer NAME:")))
                .andExpect(content().string(containsString("Customer SURNAME:")))
                .andExpect(content().string(containsString("Customer EMAIL:")))
                .andExpect(content().string(containsString("Customer PHONE:")));
    }

    @Test
    public void whenFormSent_AndWrongFillInForm_ThenErrorMessagePresent() throws Exception {
        mockMvc.perform(post("/customer/registration")
                        .param("name", "1")
                        .param("surname", "1")
                        .param("phone", "1112223344")
                        .param("email", "some_new_email@mail.com")
                        .param("password", "Helk34fw_wreJ4"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("nameError"))
                .andExpect(model().attributeExists("surnameError"));
    }

    @Test
    public void whenCorrectFormSent_AndEmailAlreadyPresentInDB_ThenErrorMessagePresent() throws Exception {
        Account accountWithId = createAccount();
        accountWithId.setId(1L);
        when(registrationCheckService.isEmailExists("some_new_email@mail.com")).thenReturn(true);
        mockMvc.perform(post("/customer/registration")
                        .param("name", "John")
                        .param("surname", "Snow")
                        .param("phone", "1112223344")
                        .param("email", "some_new_email@mail.com")
                        .param("password", "Helk34fw_wreJ4"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("emailExistsError"));
    }

    @Test
    public void whenCorrectFormSent_AndPhoneAlreadyPresentInDB_ThenErrorMessagePresent() throws Exception {
        Customer customerWithId = createCustomer();
        customerWithId.setId(1L);
        when(registrationCheckService.isCustomerPhoneExists("1112223344")).thenReturn(true);
        mockMvc.perform(post("/customer/registration")
                        .param("name", "John")
                        .param("surname", "Snow")
                        .param("phone", "1112223344")
                        .param("email", "some_new_email@mail.com")
                        .param("password", "Helk34fw_wreJ4"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("phoneExistsError"));
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

    private CustomerDTO createCustomerDTO() {
        AccountDTO accountDTO = new AccountDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        accountDTO.setId(100L);
        accountDTO.setEmail("some_new_email@mail.com");
        accountDTO.setCreatedAt(LocalDateTime.now());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);

        customerDTO.setId(100L);
        customerDTO.setAccountDTO(accountDTO);
        customerDTO.setName("Name");
        customerDTO.setSurname("Surname");
        customerDTO.setPhone("000-11-22");

        return customerDTO;
    }
}

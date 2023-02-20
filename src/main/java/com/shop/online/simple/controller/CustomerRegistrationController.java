package com.shop.online.simple.controller;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.dto.CustomerCreationDTO;
import com.shop.online.simple.entity.dto.mapper.CustomerDTOMapper;
import com.shop.online.simple.service.RegistrationCheckService;
import com.shop.online.simple.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;


@Controller
@RequestMapping("/customer")
public class CustomerRegistrationController {
    private final transient RegistrationService registrationService;
    private final transient CustomerDTOMapper customerDTOMapper;
    private final transient RegistrationCheckService registrationCheckService;
    private static final String CUSTOMER_ATTR_STR = "customer";
    private static final String CUSTOMER_REGISTRATION = "customer-registration";

    public CustomerRegistrationController(final RegistrationService registrationService,
                                          final CustomerDTOMapper customerDTOMapper,
                                          final RegistrationCheckService registrationCheckService) {
        this.registrationService = registrationService;
        this.customerDTOMapper = customerDTOMapper;
        this.registrationCheckService = registrationCheckService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage(final Model model) {
        model.addAttribute(CUSTOMER_ATTR_STR, new CustomerCreationDTO());
        return CUSTOMER_REGISTRATION;
    }

    @PostMapping("/registration")
    public String registerCustomer(@Valid final CustomerCreationDTO customerCreationDTO, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            final String[] fields = {"name", "surname", "phone", "email", "password"};
            Arrays.stream(fields).forEach(field -> populateErrors(field, bindingResult, model));
            model.addAttribute(CUSTOMER_ATTR_STR, customerCreationDTO);

            return CUSTOMER_REGISTRATION;
        }

        //check if email not present
        if (registrationCheckService.isEmailExists(customerCreationDTO.getEmail())) {
            model.addAttribute("emailExistsError", "Account with this email already exists.");
            model.addAttribute(CUSTOMER_ATTR_STR, customerCreationDTO);

            return CUSTOMER_REGISTRATION;
        }
        //check if phone number not present
        if (registrationCheckService.isCustomerPhoneExists(customerCreationDTO.getPhone())) {
            model.addAttribute("phoneExistsError", "Account with this phone already exists.");
            model.addAttribute(CUSTOMER_ATTR_STR, customerCreationDTO);

            return CUSTOMER_REGISTRATION;
        }

        //convert from CustomerCreationDTO to Entity, save and get result with id
        final Customer customer = registrationService.createNewCustomer(customerDTOMapper.toCustomer(customerCreationDTO));
        model.addAttribute(CUSTOMER_ATTR_STR, customerDTOMapper.toCustomerDTO(customer));

        return "customer-registration-done";
    }

    private void populateErrors(final String field, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasFieldErrors(field)) {
            model.addAttribute(field + "Error", bindingResult.getFieldError(field).getDefaultMessage());
        }
    }
}

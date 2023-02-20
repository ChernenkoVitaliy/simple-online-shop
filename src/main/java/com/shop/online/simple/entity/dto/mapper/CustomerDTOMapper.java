package com.shop.online.simple.entity.dto.mapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.dto.CustomerCreationDTO;
import com.shop.online.simple.entity.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper {
    private final transient AccountDTOMapper accountDTOMapper;

    public CustomerDTOMapper(AccountDTOMapper accountDTOMapper) {
        this.accountDTOMapper = accountDTOMapper;
    }

    public Customer toCustomer(final CustomerCreationDTO customerCreationDTO) {
        final Customer customer = new Customer();
        final Account account = new Account(customerCreationDTO.getEmail(), customerCreationDTO.getPassword());
        customer.setAccount(account);
        customer.setName(customerCreationDTO.getName());
        customer.setSurname(customerCreationDTO.getSurname());
        customer.setPhone(customerCreationDTO.getPhone());

        return customer;
    }

    public CustomerDTO toCustomerDTO(final Customer customer) {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setAccountDTO(accountDTOMapper.toAccountDTO(customer.getAccount()));
        customerDTO.setName(customer.getName());
        customerDTO.setSurname(customer.getSurname());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setDeliveryAddress(customer.getDeliveryAddress());
        customerDTO.setCart(customer.getCart());
        customerDTO.setWishList(customer.getWishList());

        return customerDTO;
    }
}

package com.shop.online.simple;

import com.shop.online.simple.repository.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class SpringBootContextTestConfiguration {
    @Primary
    @Bean
    public CustomerRepository mockedCustomerRepository() {
        return mock(CustomerRepository.class);
    }
    @Primary
    @Bean
    public OrderRepository mockedOrderRepository() {
        return mock(OrderRepository.class);
    }
    @Primary
    @Bean
    public WishListRepository mockedWishListRepository() {
        return mock(WishListRepository.class);
    }
}
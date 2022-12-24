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
    public CartRepository mockedCartRepository() {
        return mock(CartRepository.class);
    }
    @Primary
    @Bean
    public CustomerRepository mockedCustomerRepository() {
        return mock(CustomerRepository.class);
    }
    @Primary
    @Bean
    public FeedbackRepository mockedFeedbackRepository() {
        return mock(FeedbackRepository.class);
    }
    @Primary
    @Bean
    public OrderRepository mockedOrderRepository() {
        return mock(OrderRepository.class);
    }
    @Primary
    @Bean
    public ProductRepository mockedProductRepository() {
        return mock(ProductRepository.class);
    }
    @Primary
    @Bean
    public WishListRepository mockedWishListRepository() {
        return mock(WishListRepository.class);
    }
}

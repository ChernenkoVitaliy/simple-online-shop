package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.*;
import com.shop.online.simple.exception.EmptyCartException;
import com.shop.online.simple.exception.ProductAlreadyAddedException;
import com.shop.online.simple.repository.CartRepository;
import com.shop.online.simple.repository.OrderRepository;
import com.shop.online.simple.repository.WishListRepository;
import com.shop.online.simple.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
    private CustomerService customerService;
    private CartRepository cartRepository = mock(CartRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private WishListRepository wishListRepository = mock(WishListRepository.class);
    private Product product;
    private Account account;
    private Customer customer;
    private Order order;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerServiceImpl(cartRepository, orderRepository, wishListRepository);
        product = createProduct();
        account = createAccount();
        customer = createCustomer();
        order = createOrder();
    }



    @Test
    public void whenAddProductToCart_ThenProductPresentInCart() {
        customerService.addToCart(product, customer);

        assertTrue(customer.getCart().getProducts().contains(product));
    }

    @Test
    public void addToCartMethod_ShouldThrowsException_WhenProductIsNull() {
        assertThatThrownBy(() -> customerService.addToCart(null, customer))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addToCartMethod_ShouldThrowsException_WhenCustomerIsNull() {
        assertThatThrownBy(() -> customerService.addToCart(product, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addToCartMethod_ShouldThrowsException_WhenProductAlreadyPresentInCart() {
        customerService.addToCart(product, customer);

        assertThatThrownBy(() -> customerService.addToCart(product, customer))
                .isInstanceOf(ProductAlreadyAddedException.class);
    }

    @Test
    public void whenDeleteProductFromCart_ThenCartDoNotContainsThisProduct() {
        customer.getCart().getProducts().add(product);

        customerService.deleteProductFromCart(product, customer);

        assertFalse(customer.getCart().getProducts().contains(product));
    }

    @Test
    public void whenDeleteProductFromCart_AndCartDoNotContainsThisProduct_ThenReturnFalse() {
        var result = customerService.deleteProductFromCart(product, customer);

        assertFalse(result);
    }

    @Test
    public void deleteProductFromCartMethod_ShouldThrowsException_WhenProductIsNull() {
        assertThatThrownBy(() -> customerService.deleteProductFromCart(null, customer))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void deleteProductFromCartMethod_ShouldThrowsException_WhenCustomerIsNul() {
        assertThatThrownBy(() -> customerService.deleteProductFromCart(product, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenCreateOrder_AndCartContainsProducts_ThenReturnOrderWithStatusNew() {
        customer.getCart().getProducts().add(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        var result = customerService.createOrder(customer);

        assertEquals(result.getOrderStatus(), OrderStatus.NEW);
    }

    @Test
    public void whenCreateOrder_AndCartDoNotContainsProducts_ThenThrowException() {
        customer.getCart().getProducts().clear();

        assertThatThrownBy(() -> customerService.createOrder(customer))
                .isInstanceOf(EmptyCartException.class);
    }

    @Test
    public void whenAddProductToWishList_ThenProductPresentInWishList() {
        var result = customerService.addProductToWishList(product, customer);

        assertTrue(result.getProducts().contains(product));
    }

    @Test
    public void addProductToWishListMethod_ShouldThrowsException_WhenProductIsNul() {
        assertThatThrownBy(() -> customerService.addProductToWishList(null, customer))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addProductToWishListMethod_ShouldThrowsException_WhenCustomerIsNul() {
        assertThatThrownBy(() -> customerService.addProductToWishList(product, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenAddProductToWishList_AndProductAlreadyPresent_ThenThrowException() {
        customerService.addProductToWishList(product, customer);

        assertThatThrownBy(() -> customerService.addProductToWishList(product, customer))
                .isInstanceOf(ProductAlreadyAddedException.class);
    }

    private Product createProduct() {
        return new Product("Product name", "Some product description", 10_000L);
    }

    private Account createAccount() {
        return new Account("some_email@some.com", "passWord");
    }

    private Customer createCustomer() {
        return new Customer(account, "John", "Smith", "123-45-67");
    }

    private Order createOrder() {
        return new Order(customer, LocalDateTime.now(), customer.getCart().getProducts(), OrderStatus.NEW);
    }
}

package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.*;
import com.shop.online.simple.entity.enums.OrderStatus;
import com.shop.online.simple.exception.EmptyCartException;
import com.shop.online.simple.exception.ProductAlreadyAddedException;
import com.shop.online.simple.repository.CustomerRepository;
import com.shop.online.simple.repository.OrderRepository;
import com.shop.online.simple.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final transient CustomerRepository customerRepository;
    private final transient OrderRepository orderRepository;
    private final static String PRODUCT_ERR_TEXT = "Product must not be null.";
    private final static String CUSTOMER_ERR_TEXT = "Customer must not be null.";

    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    /*Client can add products to cart*/
    @Override
    public Product addToCart(final Product product, final Customer customer) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        if (customer.getCart().getProducts().contains(product)) {
            throw new ProductAlreadyAddedException("Product already added to cart");
        }

        customer.getCart().getProducts().add(product);
        customerRepository.save(customer);

        return product;
    }

    /*Customer can delete products from cart*/
    @Override
    public void deleteProductFromCart(final Product product, final Customer customer) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        final Cart cart = customer.getCart();

        cart.getProducts().remove(product);
        customerRepository.save(customer);
    }

    /*Customer can create orders*/
    @Override
    public Order createOrder(final Customer customer) {
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        if(customer.getCart().getProducts().size() == 0) {
            throw new EmptyCartException("Can't create order. Cart is empty.");
        }

        final Order newOrder = new Order(customer, customer.getCart().getProducts(), OrderStatus.NEW);
        orderRepository.save(newOrder);

        return newOrder;
    }

    /*Customer can add products to wish list*/
    @Override
    public List<Product> addProductToWishList(final Product product, final Customer customer) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        final List<Product> wishList = customer.getWishList();

        if (wishList.contains(product)) {
            throw new ProductAlreadyAddedException("Product already added to wish list");
        }

        wishList.add(product);
        customerRepository.save(customer);

        return wishList;
    }
}

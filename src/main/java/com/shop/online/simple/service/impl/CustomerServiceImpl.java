package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.*;
import com.shop.online.simple.entity.enums.OrderStatus;
import com.shop.online.simple.exception.EmptyCartException;
import com.shop.online.simple.exception.ProductAlreadyAddedException;
import com.shop.online.simple.repository.CartRepository;
import com.shop.online.simple.repository.OrderRepository;
import com.shop.online.simple.repository.WishListRepository;
import com.shop.online.simple.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final transient CartRepository cartRepository;
    private final transient OrderRepository orderRepository;
    private final transient WishListRepository wishListRepo;
    private final static String PRODUCT_ERR_TEXT = "Product must not be null.";
    private final static String CUSTOMER_ERR_TEXT = "Customer must not be null.";

    public CustomerServiceImpl(final CartRepository cartRepository,
                               final OrderRepository orderRepository,
                               final WishListRepository wishListRepo) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.wishListRepo = wishListRepo;
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
        cartRepository.save(customer);

        return product;
    }

    /*Customer can delete products from cart*/
    @Override
    public boolean deleteProductFromCart(final Product product, final Customer customer) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        final Cart cart = customer.getCart();

        if (cart.getProducts().remove(product)) {
            cartRepository.deleteProductFromCart(cart, product);

            return true;
        }

        return false;
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
    public WishList addProductToWishList(final Product product, final Customer customer) {
        Objects.requireNonNull(product, PRODUCT_ERR_TEXT);
        Objects.requireNonNull(customer, CUSTOMER_ERR_TEXT);

        final WishList wishList = customer.getWishList();

        if (wishList.getProducts().contains(product)) {
            throw new ProductAlreadyAddedException("Product already added to wish list");

        }

        wishList.getProducts().add(product);
        wishListRepo.update(wishList);

        return wishList;
    }
}

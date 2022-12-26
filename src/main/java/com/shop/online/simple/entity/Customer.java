package com.shop.online.simple.entity;

import java.util.Objects;

public class Customer {
    private Account account;
    private String name;
    private String surname;
    private String phone;
    private Cart cart;
    private Address deliveryAddress;
    private WishList wishList;

    public Customer() {}

    public Customer(Account account, String name, String surname, String phone) {
        this.account = account;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.cart = new Cart();
        this.wishList = new WishList();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(account, customer.account) && name.equals(customer.name) && surname.equals(customer.surname) && Objects.equals(phone, customer.phone) && Objects.equals(cart, customer.cart) && Objects.equals(deliveryAddress, customer.deliveryAddress) && Objects.equals(wishList, customer.wishList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, name, surname, phone, cart, deliveryAddress, wishList);
    }
}

package com.shop.online.simple.entity.dto;

import com.shop.online.simple.entity.Address;
import com.shop.online.simple.entity.Cart;

import java.util.List;
import java.util.Objects;

public class CustomerDTO {
    private long id;
    private AccountDTO accountDTO;
    private String name;
    private String surname;
    private String phone;
    private Cart cart;
    private Address deliveryAddress;
    private List<ProductDTO> wishList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
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

    public List<ProductDTO> getWishList() {
        return wishList;
    }

    public void setWishList(List<ProductDTO> wishList) {
        this.wishList = wishList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return id == that.id && Objects.equals(accountDTO, that.accountDTO) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(phone, that.phone) && Objects.equals(cart, that.cart) && Objects.equals(deliveryAddress, that.deliveryAddress) && Objects.equals(wishList, that.wishList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountDTO, name, surname, phone, cart, deliveryAddress, wishList);
    }
}

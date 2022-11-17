package com.shop.online.simple.entity;

import java.util.Objects;

public class Address {
    private String country;
    private String city;
    private String street;
    private String phone;

    public Address(String country, String city, String street, String phone) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return country.equals(address.country) && city.equals(address.city) && street.equals(address.street) && phone.equals(address.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, phone);
    }
}

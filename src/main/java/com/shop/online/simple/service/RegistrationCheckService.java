package com.shop.online.simple.service;

public interface RegistrationCheckService {

    boolean isEmailExists(String email);

    boolean isCustomerPhoneExists(String phone);
}

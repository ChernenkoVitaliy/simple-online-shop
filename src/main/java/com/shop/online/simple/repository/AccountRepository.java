package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Account;

import java.util.Optional;

public interface AccountRepository extends GeneralRepository<Account> {

    Optional<Account> findByEmail(String email);
}

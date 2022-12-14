package com.shop.online.simple.entity;

import com.shop.online.simple.entity.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Account {
    private long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private AccountStatus accountStatus;

    public Account() {}

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        createdAt = LocalDateTime.now();
        accountStatus = AccountStatus.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && email.equals(account.email) && password.equals(account.password) && createdAt.equals(account.createdAt) && accountStatus == account.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, createdAt, accountStatus);
    }
}

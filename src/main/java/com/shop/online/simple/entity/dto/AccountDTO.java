package com.shop.online.simple.entity.dto;

import com.shop.online.simple.entity.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class AccountDTO {
    private long id;
    private String email;
    private LocalDateTime createdAt;
    private AccountStatus accountStatus;

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
        AccountDTO that = (AccountDTO) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(createdAt, that.createdAt) && accountStatus == that.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, createdAt, accountStatus);
    }
}

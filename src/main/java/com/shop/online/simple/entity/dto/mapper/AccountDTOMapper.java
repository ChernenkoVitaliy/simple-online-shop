package com.shop.online.simple.entity.dto.mapper;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOMapper {

    public AccountDTO toAccountDTO(final Account account) {
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setAccountStatus(account.getAccountStatus());

        return accountDTO;
    }
}

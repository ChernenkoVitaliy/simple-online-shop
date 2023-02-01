package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.enums.AccountStatus;
import com.shop.online.simple.repository.SellerRepository;
import com.shop.online.simple.repository.rowmapper.AccountRowMapper;
import com.shop.online.simple.repository.rowmapper.ProductRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SellerRepositoryImplTest {
    private Seller seller = createSeller();
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndSellerPresentInDataBase_ThenTrue() {
        Optional<Seller> result = sellerRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndSellersPresentInDataBase_ThenListNotEmpty() {
        List<Seller> result = sellerRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewSeller_ThenSellerPresentInDataBase() {
        Seller newSeller = createSeller();
        jdbcTemplate.update("INSERT INTO account(email, password, created_at, account_status) VALUES(?, ?, ?, ?);",
                newSeller.getAccount().getEmail(), newSeller.getAccount().getPassword(),
                newSeller.getAccount().getCreatedAt(), newSeller.getAccount().getAccountStatus().toString().toUpperCase());
        Account account = jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ?",
                new AccountRowMapper(), newSeller.getAccount().getEmail());
        newSeller.setAccount(account);

        sellerRepository.save(newSeller);
        Seller sellerFromDb = jdbcTemplate.queryForObject("SELECT * FROM seller WHERE account_id = ?",
                (rs, rowNum) -> {
                    Seller s = new Seller();
                    s.setId(rs.getLong("id"));
                    s.setCompanyName(rs.getString("company_name"));
                    s.setCompanyDescription(rs.getString("company_description"));
                    s.setSite(rs.getString("company_site"));
                    return s;
                }, account.getId());

        assertNotNull(sellerFromDb);
    }

    @Test
    public void whenUpdateSeller_ThenSellerUpdatedInDataBase() {
        Seller sellerForUpdating = sellerRepository.findOne(1L).get();
        sellerForUpdating.setSite("new-site.com");

        sellerRepository.update(sellerForUpdating);
        Seller updatedSeller = sellerRepository.findOne(1L).get();

        assertEquals(sellerForUpdating, updatedSeller);
    }


    private Account createAccount() {
        Account account = new Account();
        account.setEmail("email@mail.com");
        account.setPassword("paSSword");
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);

        return account;
    }

    private Seller createSeller() {
        Seller seller = new Seller();
        seller.setAccount(createAccount());
        seller.setCompanyName("CompanyName");
        seller.setCompanyDescription("Some company description");
        seller.setPhones(new HashSet<>(List.of("111-11-1112")));
        seller.setSite("company.com");

        return seller;
    }
}

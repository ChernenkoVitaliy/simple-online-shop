package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class SellerPhonesRepositoryImplIntegrationTest {
    @Autowired
    private SellerPhonesRepositoryImpl sellerPhonesRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindAll_AndSellerPhonesPresentInDataBase_ThenTrue() {
        List<String> result = sellerPhonesRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSavePhoneNumber_ThenItPresentInDataBase() {
        String phoneForSaving = "111-11-11";
        Seller seller = createSellerWithId(1L);
        sellerPhonesRepository.save(phoneForSaving, seller);

        String phoneFromDB = jdbcTemplate.queryForObject(
                "SELECT seller_phones.phone FROM seller_phones WHERE phone = ?;",
                String.class, phoneForSaving);

        assertEquals(phoneForSaving, phoneFromDB);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteSellerPhone_ThenItNotPresentInDataBase() {
        String phoneForDeleting = "123-45-67";
        sellerPhonesRepository.delete(phoneForDeleting);

        List<String> phonesFromDB = jdbcTemplate.query(
                "SELECT seller_phones.phone FROM seller_phones WHERE phone = ?;",
                (rs, rowNum) -> rs.getString("phone"), phoneForDeleting);

        assertTrue(phonesFromDB.isEmpty());
    }

    @Test
    public void whenFindBySellerId_AndItPresentsInDatabase_ThenReturnListOfPhoneNumbers() {
        List<String> result = sellerPhonesRepository.findBySellerId(1L);

        assertTrue(result.size() > 0);
    }

    private Seller createSellerWithId(long id) {
        Account account = new Account("some@mail.com", "paSSworD");
        account.setId(1L);
        Set<String> phones = new HashSet<>(Arrays.asList("111-11-11", "222-22-22"));
        Seller sellerWithId = new Seller(account,
                "SomeCompany",
                "SomeCompany description",
                phones,
                "company.com");
        sellerWithId.setId(id);

        return sellerWithId;
    }
}

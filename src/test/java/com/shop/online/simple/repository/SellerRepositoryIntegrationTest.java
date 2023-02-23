package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SellerRepositoryIntegrationTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void whenFindOne_AndSellerPresentInDataBase_ThenTrue() {
        Optional<Seller> result = sellerRepository.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindOne_AndSellerNotPresentInDataBase_ThenTrue() {
        Optional<Seller> result = sellerRepository.findById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindAll_AndSellersPresentInDataBase_ThenListNotEmpty() {
        List<Seller> result = sellerRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewSeller_ThenSellerPresentInDataBase() {
        Account account = createAccount();
        Seller newSeller = createSeller();
        newSeller.setAccount(account);

        Seller result = sellerRepository.save(newSeller);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenUpdateSeller_ThenSellerUpdatedInDataBase() {
        Seller sellerForUpdating = sellerRepository.findById(1L).get();
        sellerForUpdating.setSite("new-site.com");

        sellerRepository.save(sellerForUpdating);
        Seller updatedSeller = sellerRepository.findById(1L).get();

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
        seller.setSite("company.com");
        seller.setPhones(new HashSet<>(List.of("111-11-1112")));

        return seller;
    }
}
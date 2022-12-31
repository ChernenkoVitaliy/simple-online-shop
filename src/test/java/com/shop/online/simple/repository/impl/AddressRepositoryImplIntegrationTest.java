package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Address;
import com.shop.online.simple.repository.rowmapper.AddressRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AddressRepositoryImplIntegrationTest {
    @Autowired
    private AddressRepositoryImpl addressRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void whenFindOne_AndAddressPresentInDataBase_ThenTrue() {
        Optional<Address> result = addressRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndAddressesPresentInDataBase_ThenTrue() {
        List<Address> result = addressRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewAddress_ThenAddressPresentInDataBase() {
        Address newAddress = new Address("Ukraine", "Kyiv", "1st avenue");
        addressRepository.save(newAddress);

        List<Address> result = jdbcTemplate.query("SELECT * FROM address WHERE street = ?",
                new AddressRowMapper(), newAddress.getStreet());

        assertEquals(result.get(0).getStreet(), newAddress.getStreet());
    }

    @Test
    public void whenUpdateAddress_ThenAddressUpdatedInDataBase() {
        Address addressForUpdating = addressRepository.findOne(1L).get();
        addressForUpdating.setStreet("newUpdatedStreet");

        addressRepository.update(addressForUpdating);
        Address updatedAddress = addressRepository.findOne(1L).get();

        assertEquals(addressForUpdating, updatedAddress);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteAddress_ThenAddressNotPresentInDataBase() {
        Address addressForDeleting = addressRepository.findOne(1L).get();

        addressRepository.delete(addressForDeleting);
        Optional<Address> result = addressRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }
}

package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AddressRepositoryIntegrationTest {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void whenFindOne_AndAddressPresentInDataBase_ThenTrue() {
        Optional<Address> result = addressRepository.findById(1L);

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

        Address result = addressRepository.save(newAddress);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenUpdateAddress_ThenAddressUpdatedInDataBase() {
        Address addressForUpdating = addressRepository.findById(1L).get();
        addressForUpdating.setStreet("newUpdatedStreet");

        addressRepository.save(addressForUpdating);
        Address updatedAddress = addressRepository.findById(1L).get();

        assertEquals(addressForUpdating, updatedAddress);
    }

    @Test
    public void whenDeleteAddress_ThenAddressNotPresentInDataBase() {
        Address addressForDeleting = addressRepository.findById(1L).get();

        addressRepository.delete(addressForDeleting);
        Optional<Address> result = addressRepository.findById(1L);

        assertTrue(result.isEmpty());
    }
}
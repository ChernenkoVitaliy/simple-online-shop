package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

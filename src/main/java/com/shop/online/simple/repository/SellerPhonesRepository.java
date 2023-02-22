package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerPhonesRepository extends JpaRepository<Seller, Long> {
}

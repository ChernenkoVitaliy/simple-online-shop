package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Tag;

import java.util.Optional;

public interface TagRepository extends GeneralRepository<Tag> {

    Optional<Tag> findByName(String name);
}

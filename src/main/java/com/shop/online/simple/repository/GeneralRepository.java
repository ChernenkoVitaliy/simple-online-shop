package com.shop.online.simple.repository;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<T> {

    Optional<T> findOne(long id);

    List<T> findAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}

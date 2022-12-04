package com.shop.online.simple.repository;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<T> {

    Optional<T> get(long id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    boolean delete(T t);
}

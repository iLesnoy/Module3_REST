package com.epam.esm.gifts.dao;


import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    List<T> findAll(Integer offset, Integer limit);

    T create(T t);

    Optional<T> findById(Long id);

    void update(T t);

    void delete(T t);

    Long findEntityNumber();
}
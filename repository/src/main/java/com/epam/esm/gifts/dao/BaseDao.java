package com.epam.esm.gifts.dao;


import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    List<T> findAll();

    T create(T t);

    Optional<T> findById(Long id);

    int update(long id,T t);

    int deleteById(Long id);
}
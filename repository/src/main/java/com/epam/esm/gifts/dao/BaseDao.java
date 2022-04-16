package com.epam.esm.gifts.dao;


import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    List<T> findAll(Integer offset, Integer limit);

    void create(T t);

    Optional<T> findById(Long id);

    void update(T t);

    int deleteById(Long id);
}
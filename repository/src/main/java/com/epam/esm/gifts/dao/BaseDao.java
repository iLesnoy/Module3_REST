package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {

    List<T> show();

    T create(T t);

    Optional<T> findById(Long id);

    void update(long id,T t);

    void deleteById(Long id);
}
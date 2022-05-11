package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.Tag;

import java.util.Optional;

public interface StatisticsDao {

    Optional<Tag> findMostPopularTag();
}

package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.Tag;

public interface StatisticsDao {

    Tag findMostPopularTag();
}

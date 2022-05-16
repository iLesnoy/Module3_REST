package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.epam.esm.gifts.dao.constants.SqlQuery.FIND_MOST_POPULAR_TAG;


public interface StatisticsRepository extends JpaRepository<Tag,Long> {

    @Query(value = FIND_MOST_POPULAR_TAG, nativeQuery = true)
    Optional<Tag> findMostPopularTag();
}

package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.config.TestConfig;
import com.epam.esm.gifts.model.Tag;
import jdk.jfr.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class StatisticsDaoImplTest {

    EntityManager entityManager;
    StatisticsDaoImpl statisticsDao;
    Tag tag;

    @Autowired
    public StatisticsDaoImplTest(EntityManager entityManager, StatisticsDaoImpl statisticsDao) {
        this.entityManager = entityManager;
        this.statisticsDao = statisticsDao;
    }

    @BeforeEach
    void setUp() {
        statisticsDao = new StatisticsDaoImpl(entityManager);
        tag= Tag.builder().id(1L).name("epam").build();
    }

    @Test
    void findMostPopularTag() {
        Optional<Tag> optionalTag = statisticsDao.findMostPopularTag();
        assertTrue(optionalTag.isPresent());
    }
}
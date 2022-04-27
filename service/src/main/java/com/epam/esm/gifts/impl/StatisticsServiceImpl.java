package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.StatisticsService;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.StatisticsDaoImpl;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsDaoImpl statisticsDao;

    @Autowired
    public StatisticsServiceImpl(StatisticsDaoImpl statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @Override
    public TagDto mostWidelyUsedTag() {
        Optional<Tag> optionalTag = statisticsDao.findMostPopularTag();
        return TagConverter.tagToDto(optionalTag.get());
    }
}



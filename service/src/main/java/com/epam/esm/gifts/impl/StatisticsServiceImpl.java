package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.StatisticsService;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.StatisticsDaoImpl;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.epam.esm.gifts.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsDaoImpl statisticsDao;

    @Autowired
    public StatisticsServiceImpl(StatisticsDaoImpl statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @Override
    public TagDto mostWidelyUsedTag() {
        return statisticsDao.findMostPopularTag()
                .map(TagConverter::tagToDto)
                .orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
    }
}



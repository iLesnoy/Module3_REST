package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.StatisticsDaoImpl;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {
    @InjectMocks
    private StatisticsServiceImpl service;
    @Mock
    private StatisticsDaoImpl statisticsDao;
    @Mock
    private TagConverter dtoConverter;

    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("name").build();
        tagDto = TagDto.builder().id(1L).name("name").build();
    }

    @Test
    void findMostPopularTag() {
        doReturn(Optional.of(tag)).when(statisticsDao).findMostPopularTag();
        doReturn(tagDto).when(dtoConverter).tagToDto(any(Tag.class));
        TagDto actual = service.mostWidelyUsedTag();
        assertEquals(tagDto, actual);
    }

    @Test
    void findMostPopularTagThrowsExceptionWithEmptyOptionalTag() {
        doReturn(Optional.empty()).when(statisticsDao).findMostPopularTag();
        SystemException thrown = assertThrows(SystemException.class, () -> service.mostWidelyUsedTag());
        assertEquals(40410, thrown.getErrorCode());
    }
}
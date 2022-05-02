package com.epam.esm.gifts.impl;


import com.epam.esm.gifts.converter.GiftCertificateConverter;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.dao.impl.TagDaoImpl;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.ExceptionCode;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.gifts.exception.ExceptionCode.TAG_INVALID_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static com.epam.esm.gifts.validator.GiftCertificateValidator.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl service;
    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private TagConverter tagConverter;

    private Tag tag;
    private TagDto tagDto;
    private CustomPageable pageable;
    private GiftCertificate giftCertificate;
    private CustomPage<TagDto> tagPage;

    @BeforeEach
    void setUp() {
        giftCertificate= GiftCertificate.builder().id(1L).name("name").build();
        tag = Tag.builder().id(1L).name("name").build();
        tagDto = TagDto.builder().id(1L).name("name").build();
        pageable = new CustomPageable();
        pageable.setPage(5);
        pageable.setSize(1);
        tagPage = new CustomPage<>(List.of(tagDto, tagDto), pageable, 15L);
    }

    @Test
    void create() {
        doReturn(tag).when(tagConverter).dtoToTag(any(TagDto.class));
        doReturn(false).when(validator).isNameValid(anyString());
        SystemException thrown = assertThrows(SystemException.class, () -> service.create(tagDto));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void createWithInvalidName() {
        doReturn(tag).when(tagConverter).dtoToTag(any(TagDto.class));
        doReturn(true).when(validator).isNameValid(anyString());
        doReturn(Optional.of(tag)).when(tagDao).findByName(anyString());
        doReturn(tagDto).when(tagConverter).tagToDto(any(Tag.class));
        TagDto actual = service.create(tagDto);
        assertEquals(tagDto, actual);
    }



    @Test
    void update() {
        doReturn(tag).when(tagConverter).dtoToTag(any(TagDto.class));
        doReturn(true).when(validator).isNameValid(anyString());
        TagDto tag = service.update(1L, tagDto);
        assertEquals(tag, tagDto);
    }

    @Test
    void updateThrowInvalidName() {
        doReturn(false).when(validator).isNameValid(anyString());
        doReturn(tag).when(tagConverter).dtoToTag(any(TagDto.class));
        SystemException thrown = assertThrows(SystemException.class, () -> service.create(tagDto));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void findAll() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(15L).when(tagDao).findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(tag, tag)).when(tagDao).findAll(anyInt(), anyInt());
        doReturn(tagDto).when(tagConverter).tagToDto(any(Tag.class));
        CustomPage<TagDto> actual = service.findAll(pageable);
        assertEquals(tagPage, actual);
    }

    @Test
    void findAllPageNotExist() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(15L).when(tagDao).findEntityNumber();
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(tag, tag)).when(tagDao).findAll(anyInt(), anyInt());
        SystemException thrown = assertThrows(SystemException.class, () -> service.findAll(pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void findAllWithInvalidPageable() {
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        SystemException thrown = assertThrows(SystemException.class, () -> service.findAll(pageable));
        assertEquals(40050, thrown.getErrorCode());
    }

    @Test
    void findById() {
        doReturn(tagDto).when(tagConverter).tagToDto(any(Tag.class));
        doReturn(Optional.of(tag)).when(tagDao).findById(anyLong());
        TagDto actual = service.findById(1L);
        assertEquals(tagDto, actual);
    }

    @Test
    void findByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(tagDao).findById(anyLong());
        SystemException thrown = assertThrows(SystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void deleteThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(tagDao).findById(anyLong());
        SystemException thrown = assertThrows(SystemException.class, () -> service.delete(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void delete() {
        doReturn(Optional.of(tag)).when(tagDao).findById(anyLong());
        doReturn(List.of(giftCertificate)).when(tagDao).isTagUsed(tag);
        doNothing().when(tagDao).delete(any(Tag.class));
        service.delete(1L);
        assertTrue(true);
    }

}
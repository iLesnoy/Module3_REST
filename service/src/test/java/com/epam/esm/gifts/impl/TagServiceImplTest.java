package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.TagToDtoConverter;
import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.EntityCreationException;
import com.epam.esm.gifts.exception.EntityInUseException;
import com.epam.esm.gifts.exception.EntityNameValidationException;
import com.epam.esm.gifts.exception.EntityNotFoundException;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static com.epam.esm.gifts.validator.GiftCertificateValidator.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private final String TAG_NAME= "tag";
    private final long TAG_ID = 1;

    private Tag tag;
    private TagDto expected;


    @InjectMocks
    TagServiceImpl service;

    @Mock
    GiftCertificateValidator validator;
    @Mock
    DtoToTagConverter dtoToTagConverter;
    @Mock
    TagToDtoConverter tagToDtoConverter;
    @Mock
    TagDao tagDao;

    @BeforeEach
    void setUp() {
       tag = new Tag(TAG_ID,TAG_NAME);
       expected = new TagDto(TAG_ID,TAG_NAME);
    }

    @Test
    void create() {
        doReturn(true).when(validator).isNameValid(Mockito.anyString(),Mockito.any(ActionType.class));
        doReturn(Optional.empty()).when(tagDao).findById(expected.getId());
        doReturn(expected).when(tagToDtoConverter).convert(Mockito.any(Tag.class));
        doReturn(tag).when(tagDao).create(Mockito.any(Tag.class));
        doReturn(tag).when(dtoToTagConverter).convert(Mockito.any(TagDto.class));
        TagDto actual = service.create(expected);
        assertEquals(expected,actual);
    }

    @Test
    void createIfGiftAlreadyExist() {
        try {
            doReturn(true).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
            doReturn(Optional.of(tag)).when(tagDao).findById(expected.getId());
            service.create(expected);
            fail("Method create() should throw EntityCreationException");
        }catch (EntityCreationException e){
            assertTrue(true);
        }
    }

    @Test
    void createThrowExceptionWhenNameInvalid() {
        doReturn(false).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
        try {
            service.create(expected);
            fail("Method create() should throw  EntityNotValidTagNameException");
        } catch (EntityNameValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void findById() {
        doReturn(expected).when(tagToDtoConverter).convert(Mockito.any(Tag.class));
        doReturn(Optional.of(tag)).when(tagDao).findById(Mockito.anyLong());
        TagDto actual = service.findById(Mockito.anyLong());
        assertEquals(expected,actual);
    }

    @Test
    void findByIdWhenTagNotFound() {
        doReturn(Optional.empty()).when(tagDao).findById(Mockito.anyLong());
        try {
            TagDto actual = service.findById(Mockito.anyLong());
            assertEquals(expected, actual);
        }catch (EntityNotFoundException e){
            assertTrue(true);
        }
    }


    @Test
    void update() {
        doReturn(Optional.of(tag)).when(tagDao).findById(Mockito.anyLong());
        doReturn(true).when(validator).isNameValid(Mockito.anyString(),Mockito.any(ActionType.class));
        doReturn(expected).when(tagToDtoConverter).convert(Mockito.any(Tag.class));
        doReturn(tag).when(dtoToTagConverter).convert(Mockito.any(TagDto.class));
        TagDto actual = service.update(TAG_ID,expected);
        assertEquals(expected,actual);

    }

    @Test
    void updateWhenTagByIdIsNotFound() {
        doReturn(Optional.empty()).when(tagDao).findById(Mockito.anyLong());
        try{
            service.update(Mockito.anyLong(),expected);
            fail("update() method should throw EntityNotFoundException");
        }catch (EntityNotFoundException e){
            assertTrue(true);
        }
    }

    @Test
    void updateWhenTagNameIsInvalid() {
        doReturn(Optional.of(tag)).when(tagDao).findById(Mockito.anyLong());
        doReturn(false).when(validator).isNameValid(TAG_NAME,ActionType.INSERT);
        try{
            service.update(Mockito.anyLong(),expected);
            fail("update() method should throw EntityNameValidationException");
        }catch (EntityNameValidationException e){
            assertTrue(true);
        }
    }

    @Test
    void delete() {
        doReturn(Optional.of(tag)).when(tagDao).findById(Mockito.anyLong());
        doReturn(expected).when(tagToDtoConverter).convert(Mockito.any(Tag.class));
        doReturn(1).when(tagDao).deleteById(Mockito.anyLong());
        TagDto actual = service.delete(Mockito.anyLong());
        assertEquals(expected, actual);
    }

    @Test
    void deleteThrowExceptionWhenTagInUse() {
        doReturn(Optional.of(tag)).when(tagDao).findById(Mockito.anyLong());
        doReturn(expected).when(tagToDtoConverter).convert(Mockito.any(Tag.class));
        doReturn(true).when(tagDao).isUsed(Mockito.anyLong());
        try {
            service.delete(TAG_ID);
            fail("Method delete should throw exception EntityInUseException");
        } catch (EntityInUseException e) {
            assertTrue(true);
        }
    }
}
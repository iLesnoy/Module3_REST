package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.GiftCertificateToDtoConverter;
import com.epam.esm.gifts.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.*;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static com.epam.esm.gifts.validator.GiftCertificateValidator.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";

    private static final long CERTIFICATE_ID = 1;
    private static final String CERTIFICATE_NAME = "certificate";
    private static final String SEARCH_PART = "part";
    private static final String DESCRIPTION = "description";
    private static final BigDecimal PRICE = new BigDecimal("10");
    private static final int DURATION = 50;
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();

    private static final String ORDER_SORT = "desc";

    private Tag tag;
    private TagDto tagDto;
    private List<Tag> tagList;
    private List<TagDto> tagDtoList;
    private GiftCertificate certificate;
    private GiftCertificateDto expected;
    private List<String> sortingFieldList;
    private List<GiftCertificateDto> expectedList;

    @InjectMocks
    private GiftCertificateServiceImpl service;
    @Mock
    private GiftCertificateDaoImpl certificateDao;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private DtoToGiftCertificateConverter toCertificateConverter;
    @Mock
    private GiftCertificateToDtoConverter toCertificateDtoConverter;
    @Mock
    private DtoToTagConverter toTagConverter;

    @BeforeEach
    void setUp() {
        /*tag = new Tag(TAG_ID, TAG_NAME);
        tagDto = new TagDto(TAG_ID, TAG_NAME);
        tagList = new ArrayList<>(List.of(tag, tag, tag));
        tagDtoList = new ArrayList<>(List.of(tagDto, tagDto, tagDto));
        certificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, DURATION, CREATION_DATE
                , LAST_UPDATE_DATE, tagList);
        expected = new GiftCertificateDto(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, DURATION, CREATION_DATE
                , LAST_UPDATE_DATE, tagDtoList);
        sortingFieldList = List.of("name", "price");
        expectedList = List.of(expected, expected, expected);*/
    }

    @Test
    void create() {
        setCheckFieldsResult();
        doReturn(certificate).when(toCertificateConverter).convert(Mockito.any(GiftCertificateDto.class));
        doReturn(certificate).when(certificateDao).create(Mockito.any(GiftCertificate.class));
        doReturn(tagList).when(certificateDao).addTagsToCertificate(Mockito.anyLong(), Mockito.anyList());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        GiftCertificateDto actual = service.create(expected);
        assertEquals(actual, expected);

    }

    @Test
    void createThrowExceptionNullArg() {
        try {
            service.create(null);
            fail("Method insert() should throw EntityCreationException");
        } catch (EntityCreationException e) {
            assertTrue(true);
        }
    }

    @Test
    void createThrowExceptionWhenNameIsInvalid() {
        doReturn(false).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
        try {
            service.create(expected);
            fail("Method insert() should throw EntityNotValidNameException");
        } catch (EntityNameValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void createThrowPriceValidationException(){
        doReturn(true).when(validator).isNameValid(Mockito.anyString(),Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDescriptionValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(false).when(validator).isPriceValid(Mockito.any(BigDecimal.class),Mockito.any(ActionType.class));
        try {
            service.create(expected);
            fail("Method insert() should throw EntityPriceValidationException");
        } catch (EntityPriceValidationException e){
            assertTrue(true);
        }
    }

    @Test
    void createThrowDurationValidationException(){
        doReturn(true).when(validator).isNameValid(Mockito.anyString(),Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDescriptionValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isPriceValid(Mockito.any(BigDecimal.class),Mockito.any(ActionType.class));
        doReturn(false).when(validator).isDurationValid(Mockito.anyInt(),Mockito.any(ActionType.class));
        try {
            service.create(expected);
            fail("Method insert() should throw EntityDurationValidationException");
        } catch (EntityDurationValidationException e){
            assertTrue(true);
        }
    }

    @Test
    void createThrowTagListValidationException(){
        doReturn(true).when(validator).isNameValid(Mockito.anyString(),Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDescriptionValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isPriceValid(Mockito.any(BigDecimal.class),Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDurationValid(Mockito.anyInt(),Mockito.any(ActionType.class));
        doReturn(false).when(validator).isTagListValid(Mockito.anyList(),Mockito.any(ActionType.class));
        try {
            service.create(expected);
            fail("Method insert() should throw EntityTagNameValidationException");
        } catch (EntityTagNameValidationException e){
            assertTrue(true);
        }
    }


    @Test
    void update() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        setCheckFieldsResult();
        GiftCertificateDto actual = service.update(expected.getId(), expected);
        assertEquals(expected, actual);
    }

    @Test
    void updateGiftWithEmptyTagList() {
        expected.setTags(List.of());
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        setCheckFieldsResult();
        GiftCertificateDto actual = service.update(expected.getId(), expected);
        assertEquals(expected, actual);
    }

    @Test
    void updateWhenGiftDtoNullTagList() {
        try {
            service.update(expected.getId(), null);
            fail("update() method should throw EntityCreationException");
        } catch (EntityCreationException e){
            assertTrue(true);
        }
    }

    @Test
    void updateWhenGiftIdIsNullTagList() {
        try {
            service.update(null, expected);
            fail("update() method should throw EntityCreationException");
        } catch (EntityCreationException e){
            assertTrue(true);
        }
    }

    @Test
    void updateWhenIdAndGiftDtoIsNullTagList() {
        try {
            service.update(null, null);
            fail("update() method should throw EntityCreationException");
        } catch (EntityCreationException e){
            assertTrue(true);
        }
    }

    @Test
    void updateThrowExceptionWhenNameInvalid() {
        doReturn(false).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
        try {
            service.update(CERTIFICATE_ID, expected);
            fail("Method insert should throw exception EntityNameValidationException");
        } catch (EntityNameValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void updateWithNullTagList(){
        expected.setTags(null);
        doReturn(true).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDescriptionValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isPriceValid(Mockito.any(BigDecimal.class), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDurationValid(Mockito.anyInt(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isTagListValid(expected.getTags(), ActionType.UPDATE);
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        GiftCertificateDto actual = service.update(expected.getId(), expected);
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        GiftCertificateDto actual = service.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByIdWhenGiftNotFound() {
        doReturn(Optional.empty()).when(certificateDao).findById(Mockito.anyLong());
        try {
            service.findById(expected.getId());
            fail("findById() method should throw EntityNotFoundException");
        } catch (EntityNotFoundException e){
            assertTrue(true);
        }
    }

    @Test
    void searchByParameters() {
        doReturn(true).when(validator).isSortOrderValid(Mockito.anyString());
        doReturn(List.of(certificate, certificate, certificate)).when(certificateDao).findByParameters(
                (Mockito.anyString()), Mockito.anyString(), Mockito.anyString(), Mockito.anyList(), Mockito.anyString());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        List<GiftCertificateDto> actualList = service.searchByParameters(TAG_NAME, SEARCH_PART, DESCRIPTION, sortingFieldList, ORDER_SORT);
        assertEquals(expectedList, actualList);
    }

    @Test
    void searchByParametersReturnEmptyList() {
        doReturn(true).when(validator).isSortOrderValid(Mockito.anyString());
        doReturn(List.of()).when(certificateDao).findByParameters(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),
                Mockito.anyList(),Mockito.anyString());
        List<GiftCertificateDto> actualList = service.searchByParameters(TAG_NAME, SEARCH_PART, DESCRIPTION, sortingFieldList, ORDER_SORT);
        assertEquals(List.of(), actualList);
    }

    @Test
    void searchByParametersWhenSortOrderIsInvalid() {
        doReturn(false).when(validator).isSortOrderValid(Mockito.anyString());
        try {
            service.searchByParameters(TAG_NAME,SEARCH_PART,DESCRIPTION, sortingFieldList, ORDER_SORT);
            fail("Method searchByParameters() should throw exception EntityNotValidNameException");
        } catch (EntityNameValidationException e) {
            assertTrue(true);
        }
    }

    @Test
    void delete() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        doReturn(1).when(certificateDao).deleteById(CERTIFICATE_ID);
        GiftCertificateDto actual = service.delete(CERTIFICATE_ID);
        assertEquals(expected, actual);
    }

    @Test
    void deleteThrowExceptionWhenResourceDoesntExist() {
        doReturn(Optional.empty()).when(certificateDao).findById(Mockito.anyLong());
        try {
            service.delete(CERTIFICATE_ID);
            fail("delete() method should throw exception EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void deleteReturnNullWhenIsNotDeleted() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(expected).when(toCertificateDtoConverter).convert(Mockito.any(GiftCertificate.class));
        doReturn(0).when(certificateDao).deleteById(CERTIFICATE_ID);
        GiftCertificateDto actual = service.delete(CERTIFICATE_ID);
        assertEquals(expected, actual);
    }

    @Test
    void updateTagListInCertificate() {
        doReturn(true).when(certificateDao).deleteAllTagsFromCertificate(CERTIFICATE_ID);
        doReturn(tagList).when(certificateDao).addTagsToCertificate(Mockito.anyLong(), Mockito.anyList());
        List<Tag> actualUpdatedTags = service.updateTagListInCertificate(CERTIFICATE_ID,tagDtoList);
        assertEquals(tagList,actualUpdatedTags);
    }

    @Test
    void updateWhenTagListEmpty() {
        doReturn(true).when(certificateDao).deleteAllTagsFromCertificate(CERTIFICATE_ID);
        doReturn(List.of()).when(certificateDao).addTagsToCertificate(Mockito.anyLong(), Mockito.anyList());
        List<Tag> actualUpdatedTags = service.updateTagListInCertificate(CERTIFICATE_ID,tagDtoList);
        assertEquals(List.of(),actualUpdatedTags);
    }

    private void setCheckFieldsResult() {
        doReturn(true).when(validator).isNameValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDescriptionValid(Mockito.anyString(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isPriceValid(Mockito.any(BigDecimal.class), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isDurationValid(Mockito.anyInt(), Mockito.any(ActionType.class));
        doReturn(true).when(validator).isTagListValid(Mockito.anyList(), Mockito.any(ActionType.class));
    }
}
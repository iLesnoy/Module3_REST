package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.GiftCertificateAttributeConverter;
import com.epam.esm.gifts.converter.GiftCertificateConverter;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.gifts.dto.*;
import com.epam.esm.gifts.exception.ExceptionCode;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
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
import java.util.*;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private Set<Tag> tagSet;
    private List<TagDto> tagDtoList;
    private GiftCertificate certificate;
    private GiftCertificateDto updatedCertificate;
    private GiftCertificateDto certificateDto;
    private List<String> sortingFieldList;
    private List<GiftCertificateDto> expectedList;
    private CustomPageable pageable;
    private CustomPage<GiftCertificateDto> page;

    @InjectMocks
    private GiftCertificateServiceImpl service;
    @Mock
    private GiftCertificateDaoImpl certificateDao;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private GiftCertificateConverter certificateConverter;
    @Mock
    private GiftCertificateAttributeConverter attributeConverter;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("tag").build();
        tagDto = new TagDto(TAG_ID, TAG_NAME);
        tagSet = new HashSet<>(List.of(tag, tag, tag));
        tagDtoList = new ArrayList<>(List.of(tagDto, tagDto, tagDto));
        certificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, DURATION, CREATION_DATE
                , LAST_UPDATE_DATE, tagSet);
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal("40"))
                .duration(5)
                .createDate(LocalDateTime.of(2001, 1, 2, 3, 4))
                .lastUpdateDate(LocalDateTime.of(2003, 1, 2, 3, 4))
                .tagDtoList(List.of(tagDto))
                .build();
        updatedCertificate = GiftCertificateDto.builder()
                .id(1L)
                .name("nama")
                .description("description")
                .price(new BigDecimal("40"))
                .duration(5)
                .createDate(LocalDateTime.of(2001, 1, 2, 3, 4))
                .lastUpdateDate(LocalDateTime.of(2003, 1, 2, 3, 4))
                .tagDtoList(List.of(tagDto))
                .build();
        pageable = CustomPageable.builder().page(1).size(20).build();
        pageable.setPage(10);
        pageable.setSize(1);
        page = new CustomPage<>(List.of(certificateDto), pageable, 30L);
        sortingFieldList = List.of("name", "price");
        expectedList = List.of(certificateDto, certificateDto);
    }

    @Test
    void create() {
        doReturn(true).when(certificateDao).isGiftNameFree(Mockito.anyString());
        doReturn(certificate).when(certificateConverter).dtoToGiftCertificate(Mockito.any(GiftCertificateDto.class));
        doReturn(tag).when(tagService).createTag(Mockito.any(Tag.class));
        doReturn(certificate).when(certificateDao).create(Mockito.any(GiftCertificate.class));
        doReturn(certificateDto).when(certificateConverter).giftCertificateToDto(Mockito.any(GiftCertificate.class));
        GiftCertificateDto actual = service.create(certificateDto);
        assertEquals(certificateDto, actual);
    }


    @Test
    void findCertificateCreateWhenNameIsDuplicate() {
        SystemException thrown = assertThrows(SystemException.class, () -> service.create(certificateDto));
        assertEquals(40911, thrown.getErrorCode());
    }


    @Test
    void createThrowExceptionNullArg() {
        try {
            service.create(null);
            fail("Method create should throw SystemException");
        } catch (SystemException e) {
            assertTrue(true);
        }
    }

    @Test
    void update() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doReturn(tag).when(tagConverter).dtoToTag(Mockito.any(TagDto.class));
        doReturn(tag).when(tagService).createTag(Mockito.any(Tag.class));
        doNothing().when(certificateDao).update(certificate);
        doReturn(updatedCertificate).when(certificateConverter).giftCertificateToDto(Mockito.any(GiftCertificate.class));
        GiftCertificateDto actual = service.update(1L, updatedCertificate);
        assertEquals(actual, updatedCertificate);
    }

    @Test
    void updateWithNullTagList() {
        try {
            GiftCertificateDto actual = service.update(1L, GiftCertificateDto.builder()
                    .id(1L).name("qwe").tagDtoList(List.of()).price(new BigDecimal("2.0")).duration(2).build());
            service.update(1L, actual);
        } catch (SystemException e) {
            assertTrue(true);
        }
    }

    @Test
    void updateThrowExceptionNullArgs() {
        try {
            service.update(null, null);
            fail("Method update should throw SystemException");
        } catch (SystemException e) {
            assertTrue(true);
        }
    }

    @Test
    void findById() {
        doReturn(certificateDto).when(certificateConverter).giftCertificateToDto(Mockito.any(GiftCertificate.class));
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        GiftCertificateDto actual = service.findById(1L);
        assertNotNull(actual);
    }

    @Test
    void findCertificateByIdWhenEntityNonExist() {
        SystemException thrown = assertThrows(SystemException.class, () -> service.findById(Mockito.anyLong()));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(certificateDao).findById(anyLong());
        SystemException thrown = assertThrows(SystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }


    @Test
    void findAll() {
        try {
            service.findAll(pageable);
            fail("command is not supported, please use searchByParameters");
        } catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    void searchByParameters() {
        doThrow(new SystemException(Mockito.anyInt())).when(service).checkSearchParams(GiftCertificateAttributeDto.builder().build(),pageable);
        doReturn(List.of(certificate)).when(certificateDao).findByAttributes(Mockito.any(GiftCertificateAttribute.class),Mockito.anyInt(),Mockito.anyInt());
        doReturn(certificateDto).when(certificateConverter).giftCertificateToDto(Mockito.any(GiftCertificate.class));
        CustomPage<GiftCertificateDto> actual = service.searchByParameters(GiftCertificateAttributeDto.builder().build(),pageable);
        assertEquals(page, actual);
    }

    @Test
    void searchByParametersThrowInvalidAttributeList() {
        SystemException thrown = assertThrows(SystemException.class, () -> service.searchByParameters(GiftCertificateAttributeDto.builder().build(),pageable));
        assertEquals(40034, thrown.getErrorCode());
    }


    @Test
    void delete() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(Mockito.anyLong());
        doNothing().when(certificateDao).delete(certificate);
        service.delete(1L);
        assertTrue(true);
    }

    private void findEntityNumber() {
        doReturn(true).when(validator).isAttributeDtoValid(Mockito.any(GiftCertificateAttributeDto.class));
        doReturn(true).when(validator).isPageDataValid(Mockito.any(CustomPageable.class));
        doReturn(GiftCertificateAttribute.builder().build()).when(attributeConverter).convert(Mockito.any(GiftCertificateAttributeDto.class));
        doReturn(30L).when(certificateDao).findEntityNumber(Mockito.any(GiftCertificateAttribute.class));
    }

    private void setInvalidName() {
        doReturn(false).when(validator).isNameValid(anyString());
    }

}
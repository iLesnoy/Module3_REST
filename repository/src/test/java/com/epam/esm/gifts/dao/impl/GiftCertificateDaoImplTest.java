package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.config.TestConfig;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
class GiftCertificateDaoImplTest {


    private static final String TAG_NAME_FOR_SEARCH = "EPAM";
    private static final String PART_OF_SEARCH = "descript";
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";

    private static final long CERTIFICATE_ID = 1;
    private static final String CERTIFICATE_NAME = "Certificate";
    private static final String DESCRIPTION = "Description";
    private static final int DURATION = 50;
    private static final BigDecimal PRICE = new BigDecimal("200");
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();

    private final GiftCertificateDaoImpl certificateDao;
    private Tag tag;
    private List<Tag> tagList;
    private GiftCertificate certificate;

    @Autowired
    GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    void setUp() {
        /*tag = new Tag(TAG_ID, TAG_NAME);
        tagList = new ArrayList<>(List.of(tag, new Tag(2, "tag1"), new Tag(3, "tag3")));
        certificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, DURATION, CREATION_DATE
                , LAST_UPDATE_DATE, tagList);*/
    }



    @Test
    void findById() {
        Optional<GiftCertificate> actual = certificateDao.findById(1L);
        GiftCertificate giftCertificate = actual.get();
        assertEquals("certificate1", giftCertificate.getName());
    }


    @Test
    void update() {
        Map<String, Object> updatedFields = new LinkedHashMap<>();
        updatedFields.put("name", "REST");
        updatedFields.put("price", new BigDecimal("25.25"));
        int condition = certificateDao.update(CERTIFICATE_ID, certificate);
        assertTrue(condition == 1);
    }

    @Test
    void deleteById() {
        int result = certificateDao.deleteById(1L);
        assertTrue(result == 1);
    }

    @Test
    void addTagsToCertificate() {
        List<Tag> updatedTags = certificateDao.addTagsToCertificate(1L,tagList);
        assertEquals(updatedTags,tagList);
    }

    @Test
    void findByAttributesByTagAndPartOFSearch() {
        List<GiftCertificate> actual = certificateDao
                .findByParameters(TAG_NAME_FOR_SEARCH, PART_OF_SEARCH,null,null, null);
        assertEquals(actual.size(), 1);
    }

    @Test
    void findByAttributesIfAllParametersNull() {
        List<GiftCertificate> actualList = certificateDao
                .findByParameters(null, null,null, null, null);
        assertEquals(actualList.size(), 2);
    }

    @Test
    void deleteReturnsTrueWithExistingCertificate() {
        int condition = certificateDao.deleteById(3L);
        assertTrue(condition == 1);
    }

    @Test
    void deleteReturnsFalseWithNonExistingCertificate() {
        int condition = certificateDao.deleteById(100L);
        assertTrue(condition == 0);
    }

    @Test
    void addTagsToCertificateReturnsTrueWithExistingCertificate() {
        List<Tag> actualList = certificateDao.addTagsToCertificate(3L, tagList);
        assertEquals(actualList, tagList);
    }

    @Test
    void deleteAllTagsFromCertificate() {
        boolean condition = certificateDao.deleteAllTagsFromCertificate(CERTIFICATE_ID);
        assertTrue(condition);
    }
}
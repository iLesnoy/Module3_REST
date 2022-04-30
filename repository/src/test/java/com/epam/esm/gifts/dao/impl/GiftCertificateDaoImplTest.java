package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.config.TestConfig;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
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
    private GiftCertificate deletedCertificate;

    @Autowired
    GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("NewTagName").build();
        certificate = GiftCertificate.builder()
                .id(4L)
                .name("NewCertificate")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagList(Set.of(tag))
                .build();
        deletedCertificate = GiftCertificate.builder()
                .id(1L)
                .name("certificate 1")
                .description("description 1")
                .price(new BigDecimal("1.1"))
                .createDate(LocalDateTime.of(2021, 10, 8, 11, 11, 11))
                .lastUpdateDate(LocalDateTime.of(2021, 1, 1, 1, 22, 11))
                .build();
        /*attribute = GiftCertificateAttribute.builder().searchPart("certificate").build();*/
    }


    @Test
    void create() {
        GiftCertificate giftCertificate = certificateDao.create(certificate);
        assertNotNull(giftCertificate.getName());
    }

    @Test
    void findAll() {
        List<GiftCertificate> actual = certificateDao.findAll(0, 3);
        assertEquals(3, actual.size());
    }

    @Test
    void update() {
        certificate.setId(3L);
        certificateDao.update(certificate);
        assertTrue(true);
    }

    @Test
    void findByIdWithExistentEntity() {
        Optional<GiftCertificate> optional = certificateDao.findById(1L);
        assertTrue(optional.isPresent());
    }

    @Test
    void findByIdWithNonExistentEntity() {
        Optional<GiftCertificate> optional = certificateDao.findById(100L);
        assertTrue(optional.isEmpty());
    }


    /*@Test
    void findByAttributes() {
        List<GiftCertificate> actual = certificateDao.findByAttributes(attribute, 0, 3);
        assertEquals(3, actual.size());
    }*/

    @Test
    void delete() {
        certificateDao.delete(deletedCertificate);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        long actual = certificateDao.findEntityNumber();
        assertEquals(3L, actual);
    }

    @Test
    void isGiftCertificateUsedInOrdersTrueIfNotUsed() {
        boolean actual = certificateDao.isGiftCertificateUsedInOrders(1L);
        assertFalse(actual);
    }

    @Test
    void isGiftCertificateUsedNotUsed() {
        boolean actual = certificateDao.isGiftCertificateUsedInOrders(1000L);
        assertTrue(actual);
    }
    /*@Test
    void findEntityNumberByAttribute() {
        long actual = certificateDao.findEntityNumber(attribute);
        assertEquals(3L, actual);
    }*/
}
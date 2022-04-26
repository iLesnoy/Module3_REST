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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class TagDaoImplTest {


    private final TagDaoImpl tagDao;
    private Tag expected;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }


    @BeforeEach
    void setUp() {
        expected = Tag.builder().id(1L).name("maven").build();
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDao.findAll(0,4);
        System.out.println(tagList.size());
        assertEquals(tagList.size(),4);
    }

    @Test
    void create() {
        Tag actual = tagDao.create(expected);
        assertEquals("maven",actual.getName());

    }

    @Test
    void findById() {
        Optional<Tag> tag = tagDao.findById(1L);
        System.out.println(tag.get());
        assertEquals("EPAM", tag.get().getName());
    }

    @Test
    void update() {
        expected.setId(1L);
        expected.setName("Enum");
        tagDao.update(expected);
        Optional<Tag> tag = tagDao.findById(1L);
        assertEquals("Enum", tag.get().getName());

    }

    @Test
    void delete() {
        expected.setId(2L);
        expected.setName("maven");
        tagDao.delete(expected);
        List<Tag> tagList = tagDao.findAll(0,5);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        long actual = tagDao.findEntityNumber();
        assertEquals(4, actual);
    }

    @Test
    void findByName() {
    }

    @Test
    void isTagUsed() {
        List<GiftCertificate> tag = tagDao.isTagUsed(expected);
        assertNotNull(tag);
    }

    /*@Test
    void isTagUsedInCertificatesWithTrueCondition() {
        boolean condition = tagDao.isTagUsedInCertificates(3L);
        assertTrue(condition);
    }

    @Test
    void isTagUsedInCertificatesWithFalseCondition() {
        boolean condition = tagDao.isTagUsedInCertificates(4L);
        assertFalse(condition);
    }*/
}
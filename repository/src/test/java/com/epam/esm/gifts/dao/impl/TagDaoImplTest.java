package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.config.TestConfig;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
class TagDaoImplTest {

    private static final long EXIST_TAG_ID = 1;
    private static final int EXPECT_LIST_SIZE = 4;
    private static final String CREATED_TAG_NAME = "tag";

    private final TagDaoImpl tagDao;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    void create() {
        Tag actual = tagDao.create(new Tag(0, CREATED_TAG_NAME));
        assertEquals(actual.getName(), CREATED_TAG_NAME);
    }

    @Test
    void findById() {
        Optional<Tag> actual = tagDao.findById(EXIST_TAG_ID);
        assertTrue(actual.isPresent());
    }

    @Test
    void FindByIdReturnsEmptyNotExistingTag() {
        Optional<Tag> actual = tagDao.findById(100L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllTags() {
        List<Tag> tags = tagDao.show();
        assertEquals(EXPECT_LIST_SIZE, tags.size());
    }

    @Test
    void deleteById() {
        int condition = tagDao.deleteById(3L);
        assertTrue(condition == 1);
    }

    @Test
    void deleteReturnFalseNotExistingTag() {
        int condition = tagDao.deleteById(100L);
        System.out.println(condition);
        assertFalse(condition != 0);
    }

    @Test
    void isInUseTag() {
        boolean condition = tagDao.isUsed(EXIST_TAG_ID);
        assertTrue(condition);
    }

    @Test
    void isExistingReturnsFalseNotExistingTag() {
        boolean condition = tagDao.isUsed(999L);
        assertFalse(condition);
    }

    @Test
    void isUsed() {
        boolean condition = tagDao.isUsed(EXIST_TAG_ID);
        assertTrue(condition);
    }

    @Test
    void isUsedReturnsFalseNotExistingTag() {
        boolean condition = tagDao.isUsed(100L);
        assertFalse(condition);
    }

    @Test
    void findOrCreateTagNotExistingTag() {
        Tag expected = new Tag(2, "REST");
        Tag actual = tagDao.findOrCreateTag(expected);
        assertEquals(expected, actual);
    }

    @Test
    void findOrCreateTagWithNotExistingTag() {
        Tag expected = new Tag(500, "simpleTag");
        Tag actual = tagDao.findOrCreateTag(expected);
        assertEquals(expected, actual);
    }
}
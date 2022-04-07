package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.config.TestConfig;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
                                                                    import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/*@ExtendWith(SpringExtension.class)*/
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("dev")
class TagDaoImplTest {

    private static final long EXISTING_TAG_ID = 1;
    private static final int EXPECTED_LIST_SIZE = 4;
    private static final String CREATED_TAG_NAME = "newTag";

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
        Optional<Tag> actual = tagDao.findById(EXISTING_TAG_ID);
        assertTrue(actual.isPresent());
    }

    @Test
    void FindByIdReturnsEmptyWithNonExistingTag() {
        Optional<Tag> actual = tagDao.findById(100L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll() {
        List<Tag> tags = tagDao.show();
        assertEquals(EXPECTED_LIST_SIZE, tags.size());
    }

    @Test
    void delete() {
        int condition = tagDao.deleteById(3L);
        assertTrue(condition == 1);
    }

    @Test
    void deleteReturnsFalseWithNonExistingTag() {
        int condition = tagDao.deleteById(100L);
        assertFalse(condition == 0);
    }

    @Test
    void isExisting() {
        boolean condition = tagDao.isUsed(EXISTING_TAG_ID);
        assertTrue(condition);
    }

    @Test
    void isExistingReturnsFalseWithNonExistingTag() {
        boolean condition = tagDao.isUsed(EXISTING_TAG_ID);
        assertFalse(condition);
    }

    @Test
    void isUsed() {
        boolean condition = tagDao.isUsed(EXISTING_TAG_ID);
        assertTrue(condition);
    }

    @Test
    void isUsedReturnsFalseWithNonExistingTag() {
        boolean condition = tagDao.isUsed(100L);
        assertFalse(condition);
    }

    @Test
    void findOrCreateTagWithExistingTag() {
        Tag expected = new Tag(2, "HR");
        Tag actual = tagDao.findOrCreateTag(expected);
        assertEquals(expected, actual);
    }

    @Test
    void findOrCreateTagWithNonExistingTag() {
        Tag expected = new Tag(4, "newTagName");
        Tag actual = tagDao.findOrCreateTag(expected);
        assertEquals(expected, actual);
    }
}
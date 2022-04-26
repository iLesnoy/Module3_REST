package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private final UserDaoImpl userDao;
    private User user;
    private Order order;


    @Autowired
    public UserDaoImplTest(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


   /* @BeforeEach
    void setUp() {
        order = Order.builder().id(2L).purchaseTime(2022-02-10 12:33:33).cost(new BigDecimal(200)).user(1L);
        user = User.builder().id(1L).name("Yaraslau").orderList();
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
        assertEquals("Enum", tag.get().getName());
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
        expected.setName("EPAM");
        tagDao.delete(expected);
        List<Tag> tagList = tagDao.findAll(0,5);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        long actual = tagDao.findEntityNumber();
        assertEquals(4, actual);
    }*/
}
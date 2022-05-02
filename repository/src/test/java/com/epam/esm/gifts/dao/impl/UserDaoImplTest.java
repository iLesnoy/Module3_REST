package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private final UserDaoImpl userDao;
    private User user;
    private Order order;
    private Tag tag;
    private Set<Tag> tagSet;
    private GiftCertificate giftCertificate;

    @Autowired
    public UserDaoImplTest(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("tag").build();
        giftCertificate = GiftCertificate.builder().id(1L).name("gift")
                .price(new BigDecimal(200))
                .createDate(LocalDateTime.now())
                .tagList(tagSet).build();
        order = Order.builder().id(1L)
                .cost(new BigDecimal(200))
                .purchaseTime(LocalDateTime.now())
                .certificateList(List.of(giftCertificate)).build();
        user = User.builder().id(1L).name("Yaraslau").orderList(List.of(order)).build();
        order = Order.builder().id(2L).purchaseTime(LocalDateTime.now()).cost(new BigDecimal(200)).user(user).build();
    }


    @Test
    void findAll() {
        List<User> userList = userDao.findAll(0,5);
        assertEquals(3,userList.size());
    }


    @Test
    void userOrderNumber() {
    }

    @Test
    void finUserOrder() {
    }
}
package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.config.TestConfig;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class OrderDaoImplTest {

    OrderDaoImpl orderDao;
    Order order;
    Set<Tag>tagSet;
    GiftCertificate giftCertificate;

    @Autowired
    public OrderDaoImplTest(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }

    @BeforeEach
    void setUp() {
        tagSet = Set.of(Tag.builder().id(1L).name("tag").build());
        giftCertificate = GiftCertificate.builder().id(1L).name("gift")
                .price(new BigDecimal(200))
                .createDate(LocalDateTime.now())
                .tagList(tagSet).build();
        order= Order.builder().id(1L)
                .cost(new BigDecimal(200))
                .purchaseTime(LocalDateTime.now())
                .certificateList(List.of(giftCertificate)).build();
    }

    @Test
    void findAll() {
       List<Order>orderLis = orderDao.findAll(0,5);
        assertEquals(2, orderLis.size());
    }

    @Test
    void create() {
        Order actual = orderDao.create(order);
        assertEquals(actual,order);
    }

    @Test
    void findById() {
        Optional<Order> optionalOrder = orderDao.findById(1L);
        assertTrue(optionalOrder.isPresent());
    }

    @Test
    void update() {
        orderDao.update(order);
        assertTrue(true);
    }

    @Test
    void delete() {
        orderDao.delete(order);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        Long entityNumber = orderDao.findEntityNumber();
        assertEquals(2,entityNumber);
    }
}
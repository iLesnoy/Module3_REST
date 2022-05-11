package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    boolean isNameFree(String name);

    User findByName(String name);

    Long userOrderNumber(User user);

    List<Order> finUserOrder(User user,Integer offset, Integer limit);
}

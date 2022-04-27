package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;

import java.util.List;

public interface UserService extends BaseService<UserDto> {

    User findUserById(Long id);

    UserDto findByName(String name);

/*
    Long userOrderNumber(User user);
*/

    CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable);
}

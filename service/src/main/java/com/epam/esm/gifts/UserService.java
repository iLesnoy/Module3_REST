package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.model.User;

public interface UserService extends BaseService<UserDto> {

    User findUserById(Long id);

    UserDto findByName(String name);

    CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable);
}

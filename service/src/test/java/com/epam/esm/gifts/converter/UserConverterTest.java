package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserConverterTest {

    private UserConverter userConverter;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userConverter = new UserConverter();
        user = User.builder().id(1L).name("name").build();
        userDto = UserDto.builder().id(1L).name("name").build();
    }


    @Test
    void dtoToUser() {
        User actual = userConverter.dtoToUser(userDto);
        assertEquals(user, actual);
    }

    @Test
    void userToDto() {
        UserDto actual = userConverter.userToDto(user);
        assertEquals(userDto, actual);
    }
}
package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static User dtoToUser(UserDto userDto){
        return User.builder().id(userDto.getId())
                .name(userDto.getName()).build();
    }

    public static UserDto userToDto(User user){
        return UserDto.builder().id(user.getId())
                .name(user.getName()).build();
    }
}

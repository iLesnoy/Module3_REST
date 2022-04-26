package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.UserService;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.hateaos.HateoasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public UserController(UserService userService, HateoasBuilder hateoasBuilder) {
        this.userService = userService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        UserDto created = userService.create(userDto);
        hateoasBuilder.setLinks(created);
        return created;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        hateoasBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        CustomPage<UserDto> userDtos = userService.findAll(pageable);
        userDtos.getContent().forEach(hateoasBuilder::setLinks);
        return userDtos;
    }

    @GetMapping("/search/{name}")
    public UserDto findByName(@PathVariable String name) {
        UserDto userDto = userService.findByName(name);
        hateoasBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping("/{id}/orders")
    public CustomPage<ResponseOrderDto> findUserOrderList(@PathVariable Long id, CustomPageable pageable) {
        CustomPage<ResponseOrderDto> page = userService.findUserOrderList(id, pageable);
        page.getContent().forEach(hateoasBuilder::setLinks);
        return page;
    }


}
package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.UserService;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.hateaos.HateaosBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public UserController(UserService userService, HateaosBuilder hateaosBuilder) {
        this.userService = userService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        UserDto created = userService.create(userDto);
        hateaosBuilder.setLinks(created);
        return created;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        hateaosBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        CustomPage<UserDto> userDtos = userService.findAll(pageable);
        userDtos.getContent().forEach(hateaosBuilder::setLinks);
        return userDtos;
    }

    @GetMapping("/search/{name}")
    public UserDto findByName(@PathVariable String name) {
        UserDto userDto = userService.findByName(name);
        hateaosBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping("/{id}/orders")
    public CustomPage<ResponseOrderDto> findUserOrderList(@PathVariable Long id, CustomPageable pageable) {
        CustomPage<ResponseOrderDto> page = userService.findUserOrderList(id, pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }


}
package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.hateaos.HateoasBuilder;
import com.epam.esm.gifts.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    OrderServiceImpl orderService;
    HateoasBuilder hateoasBuilder;

    @Autowired
    public OrderController(OrderServiceImpl orderService, HateoasBuilder hateoasBuilder) {
        this.orderService = orderService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @GetMapping
    public CustomPage<ResponseOrderDto> findAll(CustomPageable pageable){
        CustomPage<ResponseOrderDto>page = orderService.findAll(pageable);
        page.getContent().forEach(hateoasBuilder::setLinks);
        return page;
    }

    @GetMapping("/{id}")
    public ResponseOrderDto findById(@PathVariable Long id){
        ResponseOrderDto orderDto = orderService.findById(id);
        hateoasBuilder.setLinks(orderDto);
        return orderDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrderDto create(@RequestBody RequestOrderDto orderDto){
        ResponseOrderDto responseOrderDto = orderService.createOrder(orderDto);
        hateoasBuilder.setLinks(responseOrderDto);
        return responseOrderDto;
    }
}

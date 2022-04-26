package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.hateaos.HateoasBuilder;
import com.epam.esm.gifts.impl.OrderServiceImpl;
import com.epam.esm.gifts.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    OrderServiceImpl orderService;
    HateoasBuilder hateoasBuilder;

    @Autowired
    public OrderController(OrderServiceImpl orderService, HateoasBuilder hateoasBuilder) {
        this.orderService = orderService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrderDto create(@RequestBody RequestOrderDto orderDto){
        ResponseOrderDto responseOrderDto = orderService.create(orderDto);
        hateoasBuilder.setLinks(responseOrderDto);
        return responseOrderDto;
    }

    @GetMapping("/{id}")
    public ResponseOrderDto findById(@PathVariable Long id){
        Order order = orderService.findById(id);
        hateoasBuilder.setLinks(OrderConverter.orderToDto(order));
        return OrderConverter.orderToDto(order);
    }
}

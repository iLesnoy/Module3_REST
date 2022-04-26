package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.model.Order;

public interface OrderService extends BaseService<Order>{

    ResponseOrderDto create(RequestOrderDto order);
}

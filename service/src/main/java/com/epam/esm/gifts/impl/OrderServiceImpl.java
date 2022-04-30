package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.OrderService;
import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.dao.impl.OrderDaoImpl;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.gifts.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDaoImpl orderDao;
    private GiftCertificateValidator validator;
    private UserServiceImpl userService;
    private GiftCertificateServiceImpl giftCertificateService;
    private OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderDaoImpl orderDao, GiftCertificateValidator validator, UserServiceImpl userService,
                            GiftCertificateServiceImpl giftCertificateService, OrderConverter orderConverter) {
        this.orderDao = orderDao;
        this.validator = validator;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.orderConverter = orderConverter;
    }

    @Override
    public ResponseOrderDto create(ResponseOrderDto responseOrderDto) {
        throw new UnsupportedOperationException("command is not supported in OrderServiceImpl class ");
    }

    @Override
    @Transactional
    public ResponseOrderDto create(RequestOrderDto orderDto) {
        validator.checkOrderValidation(orderDto);
        User user = userService.findUserById(orderDto.getUserId());
        List<GiftCertificate> giftCertificates = orderDto.getCertificateIdList()
                .stream().map(giftCertificateService::findCertificateById).collect(Collectors.toList());
        Order order = Order.builder().user(user).certificateList(giftCertificates).build();
        return orderConverter.orderToDto(orderDao.create(order));
    }

    @Override
    public ResponseOrderDto update(Long id, ResponseOrderDto orderDto) {
        throw new UnsupportedOperationException("update method is not supported in OrderServiceImpl class");
    }

    @Override
    public ResponseOrderDto findById(Long id) {
        Optional<ResponseOrderDto> optionalOrder = Optional.of(orderConverter.orderToDto(orderDao.findById(id).get()));
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public CustomPage<ResponseOrderDto> findAll(CustomPageable pageable) {
        int offset = calculateOffset(pageable);
        long totalOrderNumber = orderDao.findEntityNumber();
        List<ResponseOrderDto>dtoList = orderDao.findAll(offset,pageable.getSize())
                .stream().map(orderConverter::orderToDto).toList();
        return new CustomPage<>(dtoList,pageable,totalOrderNumber);
    }

    @Override
    public void delete(Long id) {
        Optional<Order>order = orderDao.findById(id);
        if(order.isPresent()){
            orderDao.delete(order.get());
        }
        throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public int calculateOffset(CustomPageable pageable) {
        return OrderService.super.calculateOffset(pageable);
    }
}

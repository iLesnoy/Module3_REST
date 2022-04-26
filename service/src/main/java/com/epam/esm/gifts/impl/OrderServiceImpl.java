package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.OrderService;
import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.dao.impl.OrderDaoImpl;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.exception.ExceptionCode;
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

    @Autowired
    public OrderServiceImpl(OrderDaoImpl orderDao, GiftCertificateValidator validator,
                            UserServiceImpl userService,
                            GiftCertificateServiceImpl giftCertificateService) {
        this.orderDao = orderDao;
        this.validator = validator;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    @Transactional
    public ResponseOrderDto create(RequestOrderDto orderDto) {
        validator.checkOrderValidation(orderDto);
        User user = userService.findUserById(orderDto.getUserId());
        List<GiftCertificate>giftCertificates = orderDto.getCertificateIdList()
                .stream().map(giftCertificateService::findCertificateById).collect(Collectors.toList());
        Order order = Order.builder().user(user).certificateList(giftCertificates).build();
        return OrderConverter.orderToDto(orderDao.create(order));
    }

    @Override
    public Order update(Long id, Order order) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> optionalOrder = orderDao.findById(id);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();

        } else throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public CustomPage<Order> findAll(CustomPageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public int calculateOffset(CustomPageable pageable) {
        return OrderService.super.calculateOffset(pageable);
    }
}

package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.dao.impl.OrderDaoImpl;
import com.epam.esm.gifts.dao.impl.UserDaoImpl;
import com.epam.esm.gifts.dto.*;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl service;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserDaoImpl userDao;
    @Mock
    private OrderConverter converter;
    @Mock
    private OrderDaoImpl orderDao;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private GiftCertificateServiceImpl certificateService;

    private User user;
    private UserDto userDto;
    private GiftCertificate certificate;
    private Order order;
    private ResponseOrderDto orderDto;
    private RequestOrderDto request;
    private CustomPageable pageable;
    private CustomPage<ResponseOrderDto> orderPage;

    @BeforeEach
    public void SetUp() {
        user = User.builder().id(1L).name("UserName").build();
        userDto = UserDto.builder().id(1L).name("UserName").build();
        pageable = CustomPageable.builder().size(1).page(10).build();
        certificate = GiftCertificate.builder().id(1L).name("NewCertificate").build();
        order = Order.builder().id(1L)
                .purchaseTime((LocalDateTime.of(2001, 1, 1, 2, 3)))
                .cost(new BigDecimal("500"))
                .user(user)
                .certificateList(List.of(GiftCertificate.builder().build()))
                .build();
        orderDto = ResponseOrderDto.builder().id(1L)
                .orderDate(LocalDateTime.of(1845, 4, 5, 10, 10))
                .cost(new BigDecimal("1400"))
                .userDto(userDto)
                .certificateList(List.of(GiftCertificateDto.builder().build()))
                .build();
        request = RequestOrderDto.builder().userId(9L).certificateIdList(List.of(1L, 1L)).build();
        orderPage = new CustomPage<>(List.of(orderDto, orderDto), pageable, 15L);
    }


    @Test
    void create() {
        doReturn(true).when(validator).checkOrderValidation(Mockito.any(RequestOrderDto.class));
        doReturn(user).when(userService).findUserById(anyLong());
        doReturn(certificate).when(certificateService).findCertificateById(anyLong());
        doReturn(orderDto).when(converter).orderToDto(any(Order.class));
        doReturn(order).when(orderDao).create(any(Order.class));
        ResponseOrderDto order = service.createOrder(request);
        assertEquals(order,orderDto);
    }


    @Test
    void createThrowsExceptionWithInvalidRequestData() {
        doReturn(false).when(validator).isRequestOrderDataValid(any(RequestOrderDto.class));
        SystemException thrown = assertThrows(SystemException.class, () -> service.createOrder(request));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void update() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.update(1L, orderDto));
        assertEquals("update method is not supported in OrderServiceImpl class", thrown.getMessage());
    }

    @Test
    void findById() {
        doReturn(orderDto).when(converter).orderToDto(any(Order.class));
        doReturn(Optional.of(order)).when(orderDao).findById(anyLong());
        ResponseOrderDto actual = service.findById(1L);
        assertEquals(orderDto, actual);
    }

    @Test
    void findAll() {
        doReturn(20L).when(orderDao).findEntityNumber();
        doReturn(List.of(order,order)).when(orderDao).findAll(Mockito.anyInt(),Mockito.anyInt());
        doReturn(orderDto).when(converter).orderToDto(any(Order.class));
        CustomPage<ResponseOrderDto> customPage = service.findAll(pageable);
        assertEquals(customPage.getSize(),1);

    }

    @Test
    void delete() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.delete(1L));
        assertEquals("delete method is not supported in OrderServiceImpl class", thrown.getMessage());
    }
}
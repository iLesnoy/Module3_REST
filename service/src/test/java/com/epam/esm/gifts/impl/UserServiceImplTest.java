package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.converter.UserConverter;
import com.epam.esm.gifts.dao.impl.UserDaoImpl;
import com.epam.esm.gifts.dto.*;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;
import com.epam.esm.gifts.validator.EntityValidator;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User user;
    private Order order;
    private ResponseOrderDto responseOrderDto;
    private GiftCertificate certificate;
    private UserDto userDto;
    private CustomPageable pageable;
    private List<UserDto> userDtos;
    private List<ResponseOrderDto> orders;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDaoImpl userDao;
    @Mock
    UserConverter userConverter;
    @Mock
    EntityValidator validator;
    @Mock
    OrderConverter orderConverter;

    @BeforeEach
    void setUp() {
        orders = List.of(ResponseOrderDto.builder().id(1L).build());
        responseOrderDto = ResponseOrderDto.builder().build();
        pageable = new CustomPageable();
        certificate = GiftCertificate.builder().id(1L).name("certificate").build();
        order = Order.builder().id(1L)
                .purchaseTime((LocalDateTime.of(2001, 1, 1, 2, 3)))
                .cost(new BigDecimal("500"))
                .user(user)
                .certificateList(List.of(GiftCertificate.builder().build()))
                .build();
        user = User.builder().id(1L)
                .name("Slava")
                .orderList(List.of(order)).build();
        userDto = UserDto.builder().id(1L)
                .name("Slava").build();
        userDtos = List.of(userDto,userDto);
    }

    @Test
    void create() {
        doReturn(true).when(validator).isNameValid(Mockito.anyString());
        doReturn(true).when(userDao).isNameFree(Mockito.anyString());
        doReturn(user).when(userDao).create(Mockito.any(User.class));
        doReturn(userDto).when(userConverter).userToDto(Mockito.any(User.class));
        doReturn(user).when(userConverter).dtoToUser(Mockito.any(UserDto.class));
        UserDto actual = userService.create(userDto);
        assertEquals(actual,userDto);
    }

    @Test
    void createThrowDuplicateName() {
        doReturn(true).when(validator).isNameValid(Mockito.anyString());
        doReturn(false).when(userDao).isNameFree(Mockito.anyString());
        SystemException exception = assertThrows(SystemException.class,()-> userService.create(userDto));
        assertEquals(40911, exception.getErrorCode());
    }

    @Test
    void createThrowInvalidName() {
        doReturn(false).when(validator).isNameValid(Mockito.anyString());
        SystemException exception = assertThrows(SystemException.class,()-> userService.create(userDto));
        assertEquals(40040, exception.getErrorCode());
    }

    @Test
    void update() {
        doReturn(Optional.of(user)).when(userDao).findById(Mockito.anyLong());
        doReturn(true).when(userDao).isNameFree(Mockito.anyString());
        doReturn(user).when(userConverter).dtoToUser(Mockito.any(UserDto.class));
        doNothing().when(userDao).update(Mockito.any(User.class));
        UserDto actual = userService.update(1L,userDto);
        assertEquals(userDto,actual);
    }

    @Test
    void updateThrowNonExist() {
        doReturn(Optional.of(user)).when(userDao).findById(Mockito.anyLong());
        doReturn(false).when(userDao).isNameFree(Mockito.anyString());
        SystemException exception = assertThrows(SystemException.class,()->userService.update(1L,userDto));
        assertEquals(40410,exception.getErrorCode());
    }

    @Test
    void findById() {
        doReturn(Optional.of(user)).when(userDao).findById(Mockito.anyLong());
        doReturn(userDto).when(userConverter).userToDto(Mockito.any(User.class));
        UserDto userDto = userService.findById(1L);
        assertEquals(userDto,userDto);
    }

    @Test
    void findByIdThrowNonExistEntity() {
        doReturn(Optional.empty()).when(userDao).findById(Mockito.anyLong());
        SystemException exception = assertThrows(SystemException.class,()->userService.findById(1L));
        assertEquals(40410,exception.getErrorCode());
    }

    @Test
    void findAll() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(10L).when(userDao).findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(userDtos).when(userDao).findAll(Mockito.anyInt(),Mockito.anyInt());
        doReturn(userDto).when(userConverter).userToDto(Mockito.any(User.class));
        CustomPage<UserDto>actual = userService.findAll(pageable);
        assertEquals(pageable,actual);
    }

    @Test
    void delete() {
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doNothing().when(userDao).delete(any(User.class));
        userService.delete(1L);
        assertTrue(true);
    }

    @Test
    void deleteThrowNonExist() {
        doReturn(Optional.empty()).when(userDao).findById(anyLong());
        SystemException exception = assertThrows(SystemException.class,()->userService.delete(1L));
        assertEquals(40410,exception.getErrorCode());
    }

    @Test
    void findUserById() {
        doReturn(userDto).when(userConverter).userToDto(any(User.class));
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        UserDto actual = userService.findById(1L);
        assertEquals(userDto, actual);
    }

    @Test
    void findByName() {
        doReturn(true).when(validator).isNameValid(Mockito.anyString());
        doReturn(userDto).when(userConverter).userToDto(any(User.class));
        doReturn(user).when(userDao).findByName(Mockito.anyString());
        UserDto actual = userService.findByName("papa");
        assertEquals(userDto,actual);
    }

    @Test
    void findUserOrderList() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doReturn(10L).when(userDao).userOrderNumber(Mockito.any(User.class));
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());

        doReturn(List.of(responseOrderDto)).when(userDao).finUserOrder(Mockito.any(User.class),Mockito.anyInt(),Mockito.anyInt());
        doReturn(responseOrderDto).when(orderConverter).orderToDto(Mockito.any(Order.class));
        CustomPage<ResponseOrderDto> actual = userService.findUserOrderList(1L,pageable);
        assertEquals(orders,actual);
    }

    @Test
    void findUserOrderListThrowInvalidData() {
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        SystemException exception = assertThrows(SystemException.class,()->userService.findUserOrderList(1L, pageable));
        assertEquals(40050,exception.getErrorCode());
    }
}
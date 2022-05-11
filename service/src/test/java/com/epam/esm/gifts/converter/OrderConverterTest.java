package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.*;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.model.User;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderConverterTest {

    @InjectMocks
    private OrderConverter orderConverter;
    @Mock
    private GiftCertificateConverter certificateConverter;
    @Mock
    private UserConverter userToDtoConverter;

    private User user;
    private UserDto userDto;
    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;
    private Order order;
    private ResponseOrderDto orderDto;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(1L).name("User").build();
        userDto = UserDto.builder().id(1L).name("User").build();
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .description("FirstCertificateDescription")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(2000, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(2000, 10, 10, 10, 10))
                .tagDtoList(List.of(TagDto.builder().id(1L).name("FirstTag").build(), TagDto.builder().id(2L).name("SecondTag").build()))
                .build();
        certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("FirstCertificateDescription")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(2000, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(2000, 10, 10, 10, 10))
                .tagList(Set.of(Tag.builder().id(1L).name("FirstTag").build(), Tag.builder().id(2L).name("SecondTag").build()))
                .build();
        order = Order.builder()
                .id(1L)
                .purchaseTime(LocalDateTime.of(2000, 10, 10, 10, 10))
                .cost(new BigDecimal("10"))
                .user(user)
                .certificateList(List.of(certificate))
                .build();
        orderDto = ResponseOrderDto.builder()
                .id(1L)
                .orderDate(LocalDateTime.of(2000, 10, 10, 10, 10))
                .cost(new BigDecimal("10"))
                .userDto(userDto)
                .certificateList(List.of(certificateDto))
                .build();
    }

    @Test
    void dtoToOrder() {
        doReturn(user).when(userToDtoConverter).dtoToUser(Mockito.any(UserDto.class));
        doReturn(certificate).when(certificateConverter).dtoToGiftCertificate(Mockito.any(GiftCertificateDto.class));
        Order actual = orderConverter.dtoToOrder(orderDto);
        assertEquals(order, actual);
    }

    @Test
    void orderToDto() {
        doReturn(userDto).when(userToDtoConverter).userToDto(Mockito.any(User.class));
        doReturn(certificateDto).when(certificateConverter).giftCertificateToDto(Mockito.any(GiftCertificate.class));
        ResponseOrderDto actual = orderConverter.orderToDto(order);
        assertEquals(orderDto, actual);
    }
}
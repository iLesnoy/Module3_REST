package com.epam.esm.gifts.converter;


import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {


    UserConverter userConverter;
    GiftCertificateConverter giftCertificateConverter;

    @Autowired
    public OrderConverter(UserConverter userConverter, GiftCertificateConverter giftCertificateConverter) {
        this.userConverter = userConverter;
        this.giftCertificateConverter = giftCertificateConverter;
    }


    public static Order dtoToOrder(ResponseOrderDto order) {
        return Order.builder()
                .id(order.getId())
                .purchaseTime(order.getOrderDate())
                .cost(order.getCost())
                .user(UserConverter.dtoToUser(order.getUserDto()))
                .certificateList(order.getCertificateList().stream().map(GiftCertificateConverter::dtoToGiftCertificate).toList())
                .build();
    }
        public static ResponseOrderDto orderToDto (Order order){
            return ResponseOrderDto.builder()
                    .id(order.getId())
                    .orderDate(order.getPurchaseTime())
                    .cost(order.getCost())
                    .userDto(UserConverter.userToDto(order.getUser()))
                    .certificateList(order.getCertificateList().stream().map(GiftCertificateConverter::giftCertificateToDto).toList())
                    .build();
        }
    }
package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.model.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GiftCertificateConverter {

    public static GiftCertificateDto giftCertificateToDto(GiftCertificate giftCertificate){
        return GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .description(giftCertificate.getDescription())
                .tagDtoList(giftCertificate.getTagList().stream().map(TagConverter::tagToDto).toList())
                .build();
    }

    public static GiftCertificate dtoToGiftCertificate(GiftCertificateDto giftCertificateDto){
        return GiftCertificate.builder()
                .id(giftCertificateDto.getId())
                .name(giftCertificateDto.getName())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .description(giftCertificateDto.getDescription())
                .tagList(giftCertificateDto.getTagDtoList().stream().map(TagConverter::dtoToTag).collect(Collectors.toSet()))
                .build();
    }
}

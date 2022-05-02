package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GiftCertificateConverterTest {

    @InjectMocks
    private GiftCertificateConverter converter;
    @Mock
    private TagConverter tagConverter;

    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {

        tag = Tag.builder().id(1L).name("tagFirst").build();
        tagDto = TagDto.builder().id(1L).name("tag").build();
        certificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("descr")
                .price(new BigDecimal("100"))
                .duration(5)
                .createDate(LocalDateTime.of(2001, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(2001, 10, 10, 10, 10))
                .tagList(Set.of(tag))
                .build();
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .description("descr")
                .price(new BigDecimal("100"))
                .duration(5)
                .createDate(LocalDateTime.of(2001, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(2001, 10, 10, 10, 10))
                .tagDtoList(List.of(tagDto))
                .build();
    }

    @Test
    void giftCertificateToDto() {
        doReturn(tagDto).when(tagConverter).tagToDto(any(Tag.class));
        GiftCertificateDto actual = converter.giftCertificateToDto(certificate);
        assertEquals(certificateDto,actual);
    }

    @Test
    void dtoToGiftCertificate() {
        doReturn(tag).when(tagConverter).dtoToTag(any(TagDto.class));
        GiftCertificate actual = converter.dtoToGiftCertificate(certificateDto);
        assertEquals(certificate,actual);
    }
}
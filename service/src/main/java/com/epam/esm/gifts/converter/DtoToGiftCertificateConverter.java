package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToGiftCertificateConverter implements Converter<GiftCertificateDto, GiftCertificate> {
    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public DtoToGiftCertificateConverter(DtoToTagConverter dtoToTagConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public GiftCertificate convert(GiftCertificateDto source) {
        return null;
    }

    /*@Override
    public GiftCertificate convert(GiftCertificateDto certificateDto) {
        return new GiftCertificate(certificateDto.getId(), certificateDto.getName()
                , certificateDto.getDescription(), certificateDto.getPrice(), certificateDto.getDuration(), certificateDto.getCreateDate()
                , certificateDto.getLastUpdateDate(), certificateDto.getTags().stream().map(dtoToTagConverter::convert).toList());
    }*/
}
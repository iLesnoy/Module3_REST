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
        return new GiftCertificate(source.getId(), source.getName()
                , source.getDescription(), source.getPrice(), source.getDuration(), source.getCreateDate()
                , source.getLastUpdateDate(), source.getTags().stream().map(dtoToTagConverter::convert).toList());
    }
}
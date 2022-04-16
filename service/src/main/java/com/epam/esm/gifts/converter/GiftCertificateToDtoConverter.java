package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateToDtoConverter implements Converter<GiftCertificate,GiftCertificateDto> {

    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public GiftCertificateToDtoConverter(TagToDtoConverter tagToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate source) {
        return null;
    }

    /*@Override
    public GiftCertificateDto convert(GiftCertificate gift){
        return new GiftCertificateDto(gift.getId(), gift.getName()
                , gift.getDescription(), gift.getPrice(), gift.getDuration(), gift.getCreateDate()
                , gift.getLastUpdateDate(), gift.getTags().stream().map(tagToDtoConverter::convert).toList());
    }*/
}

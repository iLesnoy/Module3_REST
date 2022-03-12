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
    public GiftCertificateDto convert(GiftCertificate data){
        return new GiftCertificateDto(data.getId(), data.getName()
                , data.getDescription(), data.getPrice(), data.getDuration(), data.getCreateDate()
                , data.getLastUpdateDate(), data.getTags().stream().map(tagToDtoConverter::convert).toList());
    }
}

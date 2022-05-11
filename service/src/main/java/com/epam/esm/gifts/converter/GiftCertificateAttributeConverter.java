package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.GiftCertificateAttributeDto;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateAttributeConverter implements Converter<GiftCertificateAttributeDto, GiftCertificateAttribute> {

    @Override
    public  GiftCertificateAttribute convert(GiftCertificateAttributeDto source) {
        return GiftCertificateAttribute.builder()
                .tagNameList(source.getTagNameList())
                .searchPart(source.getSearchPart())
                .orderSort(source.getOrderSort())
                .sortingFieldList(source.getSortingFieldList())
                .build();
    }
}

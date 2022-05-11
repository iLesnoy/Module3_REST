package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.GiftCertificateAttributeDto;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    CustomPage<GiftCertificateDto> searchByParameters(GiftCertificateAttributeDto attributeDto, CustomPageable pageable);

    GiftCertificate findCertificateById(Long id);

}

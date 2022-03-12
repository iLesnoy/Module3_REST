package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.GiftCertificateDto;

public interface GiftCertificateService {

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto update(Long id,GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findById(Long id);

    void delete(Long id);
}

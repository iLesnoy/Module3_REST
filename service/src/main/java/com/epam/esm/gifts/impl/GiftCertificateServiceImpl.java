package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.GiftCertificateToDtoConverter;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;

public class GiftCertificateServiceImpl implements GiftCertificateService {


    private DtoToGiftCertificateConverter dtoToGiftCertificateConverter;
    private GiftCertificateToDtoConverter certificate;
    private DtoToTagConverter tag;

    @Autowired
    public GiftCertificateServiceImpl(DtoToGiftCertificateConverter dtoToGiftCertificateConverter,
                                      GiftCertificateToDtoConverter certificate,DtoToTagConverter tag) {
        this.dtoToGiftCertificateConverter = dtoToGiftCertificateConverter;
        this.certificate = certificate;
        this.tag = tag;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        return null;
    }

    @Override
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        return null;
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return null;
    }

    @Override
    public GiftCertificateDto delete(Long id) {
        return null;
    }
}

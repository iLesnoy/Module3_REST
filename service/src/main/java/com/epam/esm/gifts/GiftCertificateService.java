package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.model.Tag;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto update(Long id,GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> searchByParameters(String tagName, String searchPart,String description, List<String> sortingFieldList, String orderSort);

    GiftCertificateDto delete(Long id);

    boolean deleteAllTagsFromCertificate(Long id);
}

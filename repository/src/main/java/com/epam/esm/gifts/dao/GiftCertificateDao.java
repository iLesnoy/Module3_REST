package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute, Integer offset, Integer limit);

    Long findEntityNumber(GiftCertificateAttribute attribute);

    boolean isGiftNameFree(String name);

    boolean isGiftCertificateUsedInOrders(Long id);
}

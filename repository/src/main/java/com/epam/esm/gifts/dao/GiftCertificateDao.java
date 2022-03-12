package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {


    List<Tag> addTagsToCertificate(Long id, List<Tag> addedTagList);

    boolean deleteAllTagsFromCertificate(Long id);
}

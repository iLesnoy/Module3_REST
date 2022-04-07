package com.epam.esm.gifts.dao;

import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {


    List<Tag> addTagsToCertificate(Long id, List<Tag> addedTagList);

    List<GiftCertificate> findByParameters(String tagName, String searchPart, String description, List<String> sortingFieldList, String orderSort);

    boolean deleteAllTagsFromCertificate(Long id);
}

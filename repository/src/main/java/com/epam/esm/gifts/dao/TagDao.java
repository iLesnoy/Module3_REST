package com.epam.esm.gifts.dao;


import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag> {

    Optional<Tag> findByName(String name);

    List<GiftCertificate> isTagUsed(Tag tag);

}

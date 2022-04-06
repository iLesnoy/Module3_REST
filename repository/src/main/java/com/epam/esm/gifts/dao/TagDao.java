package com.epam.esm.gifts.dao;


import com.epam.esm.gifts.model.Tag;

public interface TagDao extends BaseDao<Tag> {

    Tag findOrCreateTag(Tag tag);

    boolean isUsed(Long id);
}

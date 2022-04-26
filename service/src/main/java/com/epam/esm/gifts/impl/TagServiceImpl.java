package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.TagService;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.TagDaoImpl;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static com.epam.esm.gifts.exception.ExceptionCode.*;

@Service
public class TagServiceImpl implements TagService {

    TagDaoImpl tagDao;
    TagConverter tagConverter;
    GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public TagServiceImpl(TagDaoImpl tagDao, TagConverter tagConverter, GiftCertificateValidator giftCertificateValidator) {
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag tag = createTag(TagConverter.dtoToTag(tagDto));
        return TagConverter.tagToDto(tag);
    }

    public Tag createTag(Tag tag) {
        if (GiftCertificateValidator.isNameValid(tag.getName())) {
            return tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag));
        }
        throw new SystemException(TAG_INVALID_NAME);
    }

    @Override
    public TagDto update(Long id, TagDto tagDto) {
        if (GiftCertificateValidator.isNameValid(tagDto.getName())) {
            tagDao.update(TagConverter.dtoToTag(tagDto));
        }
        throw new SystemException(TAG_INVALID_NAME);
    }

    @Override
    public TagDto findById(Long id) {
        return TagConverter.tagToDto(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isPresent()) {
            List<GiftCertificate> tagList = tagDao.isTagUsed(optionalTag.get());
            if (!tagList.isEmpty()) {
                throw new SystemException(USED_ENTITY);
            }
            tagDao.delete(optionalTag.get());
        }
        throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public TagDto findOrCreateTag(TagDto tagDto) {
        return null;
    }
}

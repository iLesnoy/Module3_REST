package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.TagService;
import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.TagToDtoConverter;
import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.*;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.esm.gifts.validator.GiftCertificateValidator.ActionType.INSERT;

@Service
public class TagServiceImpl implements TagService {

    private final GiftCertificateValidator giftCertificateValidator;
    private final DtoToTagConverter dtoToTagConverter;
    private final TagToDtoConverter tagToDtoConverter;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(GiftCertificateValidator giftCertificateValidator, DtoToTagConverter dtoToTagConverter,
                          TagToDtoConverter tagToDtoConverter,
                          TagDao tagDao) {
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagToDtoConverter = tagToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Optional<Tag> optionalTag = tagDao.findById(tagDto.getId());
        if (giftCertificateValidator.isNameValid(tagDto.getName(), INSERT)) {
            if (optionalTag.isEmpty()) {
                return tagToDtoConverter.convert(tagDao.create(dtoToTagConverter.convert(tagDto)));
            }
            throw new EntityCreationException();
        }
        throw new EntityNameValidationException();
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto tagDto) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isPresent()) {
            if (giftCertificateValidator.isNameValid(tagDto.getName(), INSERT)) {
                tagDao.update(id, dtoToTagConverter.convert(tagDto));
                return tagToDtoConverter.convert(optionalTag.get());
            }
            throw new EntityNameValidationException();
        }
        throw new EntityNotFoundException();
    }


    @Override
    @Transactional
    public TagDto findById(Long id) {
        return tagDao.findById(id).map(tagToDtoConverter::convert).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public TagDto delete(Long id) {
        TagDto deletedTag = findById(id);
        if (tagDao.isUsed(id)) {
            throw new EntityInUseException();
        } else if (tagDao.deleteById(id) != 1) {
            throw new InternalServerException();
        }
        return deletedTag;
    }

    @Override
    @Transactional
    public TagDto findOrCreateTag(TagDto tagDto) {
        if (giftCertificateValidator.isNameValid(tagDto.getName(), INSERT)) {
            return tagToDtoConverter.convert(tagDao.findOrCreateTag(dtoToTagConverter.convert(tagDto)));
        }
        throw new EntityNameValidationException();
    }
}

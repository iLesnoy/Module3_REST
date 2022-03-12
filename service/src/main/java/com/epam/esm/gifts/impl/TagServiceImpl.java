package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.TagService;
import com.epam.esm.gifts.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.GiftCertificateToDtoConverter;
import com.epam.esm.gifts.converter.TagToDtoConverter;
import com.epam.esm.gifts.dao.GiftCertificateDao;
import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.EntityCreationException;
import com.epam.esm.gifts.exception.EntityNameValidationException;
import com.epam.esm.gifts.exception.EntityNotFoundException;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import com.epam.esm.gifts.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.esm.gifts.validator.GiftCertificateValidator.ActionType.INSERT;

public class TagServiceImpl implements TagService {

    private TagValidator tagValidator;
    private DtoToTagConverter dtoToTagConverter;
    private TagToDtoConverter tagToDtoConverter;
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagValidator tagValidator, DtoToTagConverter dtoToTagConverter,
                          TagToDtoConverter tagToDtoConverter,
                          TagDao tagDao) {
        this.tagValidator = tagValidator;
        this.tagToDtoConverter = tagToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Optional<Tag> optionalTag = tagDao.findById(tagDto.getId());
        if (!tagValidator.isNameValid(tagDto.getName(), INSERT)) {
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
            if (!tagValidator.isNameValid(tagDto.getName(), INSERT)) {
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
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isPresent()) {
            tagDao.deleteById(id);
            return tagToDtoConverter.convert(optionalTag.get());
        }
        throw new EntityNotFoundException();
    }
}

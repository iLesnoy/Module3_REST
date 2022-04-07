package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gifts.converter.DtoToTagConverter;
import com.epam.esm.gifts.converter.GiftCertificateToDtoConverter;
import com.epam.esm.gifts.dao.GiftCertificateDao;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.exception.*;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {


    private final GiftCertificateValidator certificateValidator;
    private final DtoToGiftCertificateConverter dtoToGiftCertificateConverter;
    private final GiftCertificateToDtoConverter certificateToDtoConverter;
    private final DtoToTagConverter dtoToTagConverter;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateValidator certificateValidator,
                                      DtoToGiftCertificateConverter dtoToGiftCertificateConverter,
                                      GiftCertificateToDtoConverter certificateToDtoConverter,
                                      DtoToTagConverter dtoToTagConverter, GiftCertificateDao giftCertificateDao) {
        this.certificateValidator = certificateValidator;
        this.dtoToGiftCertificateConverter = dtoToGiftCertificateConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            throw new EntityCreationException();
        }
        checkEntityValidation(giftCertificateDto, GiftCertificateValidator.ActionType.INSERT);
        GiftCertificate certificate = dtoToGiftCertificateConverter.convert(giftCertificateDto);
        giftCertificateDao.create(certificate);
        List<Tag> addedTags = giftCertificateDao.addTagsToCertificate(certificate.getId(), certificate.getTags());
        certificate.setTags(addedTags);
        return certificateToDtoConverter.convert(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null || id == null) {
            throw new EntityCreationException();
        }
        checkEntityValidation(giftCertificateDto, GiftCertificateValidator.ActionType.UPDATE);
        GiftCertificate certificate = dtoToGiftCertificateConverter.convert(giftCertificateDto);
        giftCertificateDao.update(id, certificate);
        return findById(id);
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(Long id) {
        return giftCertificateDao.findById(id).map(certificateToDtoConverter::convert).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<GiftCertificateDto> searchByParameters(String tagName, String searchPart, String description,
                                                       List<String> sortingFieldList, String orderSort) {
        if (certificateValidator.isSortOrderValid(orderSort)) {
            List<GiftCertificate> giftCertificateList = giftCertificateDao.findByParameters(tagName, searchPart, description, sortingFieldList, orderSort);
            return giftCertificateList.stream().map(certificateToDtoConverter::convert).toList();
        }
        throw new EntityNameValidationException();
    }

    @Override
    @Transactional
    public GiftCertificateDto delete(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (optionalGiftCertificate.isPresent()) {
            giftCertificateDao.deleteById(id);
            return certificateToDtoConverter.convert(optionalGiftCertificate.get());
        }
        throw new EntityNotFoundException();
    }

    @Override
    public boolean deleteAllTagsFromCertificate(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if(optionalGiftCertificate.isPresent()){
            return giftCertificateDao.deleteAllTagsFromCertificate(id);
        }
        throw new EntityNotFoundException();
    }

    @Transactional
    public List<Tag> updateTagListInCertificate(Long id, List<TagDto> tags) {
        List<Tag> updatedTags;

        if (!tags.isEmpty()) {
            List<Tag> updatedTagList = tags.stream().map(dtoToTagConverter::convert).toList();
            giftCertificateDao.deleteAllTagsFromCertificate(id);
            updatedTags = giftCertificateDao.addTagsToCertificate(id, updatedTagList);
        } else {
            throw new EntityCreationException();
        }
        return updatedTags;
    }

    private void checkEntityValidation(GiftCertificateDto certificateDto, GiftCertificateValidator.ActionType actionType) throws EntityDateValidationException {
        if (!certificateValidator.isNameValid(certificateDto.getName(), actionType)) {
            throw new EntityNameValidationException();
        }
        if (!certificateValidator.isDescriptionValid(certificateDto.getDescription(), actionType)) {
            throw new EntityDescriptionValidationException();
        }
        if (!certificateValidator.isPriceValid(certificateDto.getPrice(), actionType)) {
            throw new EntityPriceValidationException();
        }
        if (!certificateValidator.isDurationValid(certificateDto.getDuration(), actionType)) {
            throw new EntityDurationValidationException();
        }
        if (!certificateValidator.isTagListValid(certificateDto.getTags(), actionType)) {
            throw new EntityTagNameValidationException();
        }
    }
}

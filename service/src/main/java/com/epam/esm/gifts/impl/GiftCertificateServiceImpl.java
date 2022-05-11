package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.converter.GiftCertificateAttributeConverter;
import com.epam.esm.gifts.converter.GiftCertificateConverter;
import com.epam.esm.gifts.converter.TagConverter;
import com.epam.esm.gifts.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.gifts.dto.*;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
import com.epam.esm.gifts.model.Tag;
import com.epam.esm.gifts.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.gifts.exception.ExceptionCode.*;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    GiftCertificateConverter giftCertificateConverter;
    TagConverter tagConverter;
    GiftCertificateAttributeConverter attributeConverter; //TODO add interface?
    GiftCertificateDaoImpl giftCertificateDao;
    TagServiceImpl tagService;
    EntityValidator validator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateConverter giftCertificateConverter, GiftCertificateAttributeConverter attributeConverter,
                                      GiftCertificateDaoImpl giftCertificateDao, TagServiceImpl tagService, EntityValidator validator,
                                      TagConverter tagConverter) {
        this.giftCertificateConverter = giftCertificateConverter;
        this.tagConverter = tagConverter;
        this.attributeConverter = attributeConverter;
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        validator.checkGiftValidation(giftCertificateDto);

        if (giftCertificateDao.isGiftNameFree(giftCertificateDto.getName())) {
            GiftCertificate giftCertificate = giftCertificateConverter.dtoToGiftCertificate(giftCertificateDto);
            setTagListCertificate(giftCertificate);
            giftCertificateDao.create(giftCertificate);
            return giftCertificateConverter.giftCertificateToDto(giftCertificate);
        }
        throw new SystemException(DUPLICATE_NAME);
    }

    private void setTagListCertificate(GiftCertificate certificate) {
        Set<Tag> tagSet = certificate.getTagList();
        tagSet = tagSet.stream().map(tagService::createTag).collect(Collectors.toCollection(HashSet::new));
        certificate.setTagList(tagSet);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        validator.checkGiftValidation(giftCertificateDto);
        GiftCertificate persistedCertificate = findCertificateById(id);

        setUpdatedFields(persistedCertificate, giftCertificateDto);
        setUpdatedTagList(persistedCertificate, giftCertificateDto.getTagDtoList());

        giftCertificateDao.update(persistedCertificate);
        return giftCertificateConverter.giftCertificateToDto(persistedCertificate);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return giftCertificateConverter.giftCertificateToDto(findCertificateById(id));
    }

    @Override
    public CustomPage<GiftCertificateDto> findAll(CustomPageable pageable) {
        throw new UnsupportedOperationException("command is not supported, please use searchByParameters");
    }

    @Override
    public GiftCertificate findCertificateById(Long id) {
        return giftCertificateDao.findById(id).orElseThrow(() -> new SystemException((NON_EXISTENT_ENTITY)));
    }


    @Override
    @Transactional
    public CustomPage<GiftCertificateDto> searchByParameters(GiftCertificateAttributeDto attributeDto, CustomPageable pageable) {
        checkSearchParams(attributeDto, pageable);
        GiftCertificateAttribute attribute = attributeConverter.convert(attributeDto);
        long totalCertificateNumber = giftCertificateDao.findEntityNumber(attribute);
        if (!validator.isPageExists(pageable, totalCertificateNumber)) {
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<GiftCertificate> certificateList = giftCertificateDao.findByAttributes(attribute, offset, pageable.getSize());
        List<GiftCertificateDto> certificateDtoList = certificateList.stream().map(giftCertificateConverter::giftCertificateToDto).toList();
        return new CustomPage<>(certificateDtoList, pageable, totalCertificateNumber);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (optionalGiftCertificate.isPresent()) {
            if (giftCertificateDao.isGiftCertificateUsedInOrders(id)) {
                giftCertificateDao.delete(optionalGiftCertificate.get());
            } else {
                throw new SystemException(USED_ENTITY);
            }
        } else {
            throw new SystemException(NON_EXISTENT_ENTITY);
        }
    }

    private void setUpdatedFields(GiftCertificate persistedCertificate, GiftCertificateDto updatedCertificateDto) {
        String name = updatedCertificateDto.getName();
        String description = updatedCertificateDto.getDescription();
        BigDecimal price = updatedCertificateDto.getPrice();
        int duration = updatedCertificateDto.getDuration();
        if (Objects.nonNull(name) && !persistedCertificate.getName().equals(name)) {
            persistedCertificate.setName(name);
        }
        if (Objects.nonNull(description) && !persistedCertificate.getDescription().equals(description)) {
            persistedCertificate.setDescription(description);
        }
        if (Objects.nonNull(price) && !persistedCertificate.getPrice().equals(price)) {
            persistedCertificate.setPrice(price);
        }
        if (duration != 0 && persistedCertificate.getDuration() != duration) {
            persistedCertificate.setDuration(duration);
        }
    }


    private void setUpdatedTagList(GiftCertificate persistedCertificate, List<TagDto> tagDtoList) {
        if (CollectionUtils.isEmpty(tagDtoList)) {
            return;
        }
        Set<Tag> updatedTagSet = tagDtoList.stream()
                .map(tagConverter::dtoToTag)
                .map(tagService::createTag)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        persistedCertificate.setTagList(updatedTagSet);
    }

    public void checkSearchParams(GiftCertificateAttributeDto attributeDto, CustomPageable pageable) {
        if (!validator.isAttributeDtoValid(attributeDto)) {
            throw new SystemException(INVALID_ATTRIBUTE_LIST);
        }
        if (!validator.isPageDataValid(pageable)) {
            throw new SystemException(INVALID_DATA_OF_PAGE);
        }
    }
}

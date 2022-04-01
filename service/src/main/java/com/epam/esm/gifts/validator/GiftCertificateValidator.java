package com.epam.esm.gifts.validator;

import com.epam.esm.gifts.dto.TagDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class GiftCertificateValidator {

    private final int MIN_PERIOD=1;
    private final int MAX_PERIOD=365;
    private final String NAME_REGEX = "[\\p{Alpha}А-Яа-я]{2,65}";
    private final String PRICE_REGEX = "^(\\d+|[\\.\\,]?\\d+){1,2}$";
    private static final String DESCRIPTION_REGEX = "[\\p{Alpha}А-Яа-я\\d-.,:;!?()\" ]{2,255}";
    private static final Set<String> PROVIDED_SORT_ORDERS = Set.of("asc","desc");

    public enum ActionType {
        INSERT, UPDATE, DELETE
    }

    private boolean isNotNullAndBlank(String field) {
        return field != null && !field.isBlank();
    }

    public boolean isTagListValid(List<TagDto> tags, ActionType type) {
        return type == ActionType.INSERT
                ? !tags.isEmpty() && tags.stream()
                .map(tag -> tag != null && isNameValid(tag.getName(), ActionType.INSERT))
                .filter(Boolean::booleanValue).count() == tags.size()
                : tags.isEmpty() || tags.stream()
                .map(tag -> tag != null && isNameValid(tag.getName(), ActionType.UPDATE))
                .filter(Boolean::booleanValue).count() == tags.size();
    }

    public boolean isNameValid(String name,ActionType actionType){
        return actionType == ActionType.INSERT
                ? isNotNullAndBlank(name) && name.matches(NAME_REGEX)
                : !isNotNullAndBlank(name) && name.matches(NAME_REGEX);
    }

    public boolean isDescriptionValid(String description,ActionType actionType){
        return actionType == ActionType.INSERT
                ? isNotNullAndBlank(description) && description.matches(DESCRIPTION_REGEX)
                : !isNotNullAndBlank(description) && description.matches(PRICE_REGEX);
    }

    public boolean isPriceValid(BigDecimal price,ActionType actionType){
        return actionType == ActionType.INSERT
                ? price !=null && String.valueOf(price).matches(PRICE_REGEX)
                : price ==null && String.valueOf(price).matches(PRICE_REGEX);
    }

    public boolean isDurationValid(int duration,ActionType actionType){
        return actionType == ActionType.INSERT
                ? duration >= MIN_PERIOD && duration <= MAX_PERIOD
                : duration == 0 || duration < MIN_PERIOD && duration > MAX_PERIOD;
    }

    public boolean isSortOrderValid(String sortOrder){
        return isNotNullAndBlank(sortOrder) || PROVIDED_SORT_ORDERS.contains(sortOrder.toLowerCase());
    }

}

package com.epam.esm.gifts.validator;

import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.GiftCertificateAttributeDto;
import com.epam.esm.gifts.dto.RequestOrderDto;
import com.epam.esm.gifts.dto.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityValidatorTest {

    private static EntityValidator validator;
    private static GiftCertificateAttributeDto attributeDto;
    private static CustomPageable pageable;
    private static RequestOrderDto orderDto;

    @BeforeAll
    static void init() {
        validator= new EntityValidator();
        attributeDto = GiftCertificateAttributeDto.builder()
                .tagNameList(List.of("one", "two", "three"))
                .searchPart("any")
                .sortingFieldList(List.of("id", "name", "description"))
                .orderSort("desc")
                .build();
        pageable = new CustomPageable();
        orderDto = RequestOrderDto.builder().userId(9L).certificateIdList(List.of(1L,3L,6L,8L,10L)).build();
    }

    private static Object[][] tagValues(){
        return new Object[][] {
                {List.of(new TagDto(7L, "lenovo")),true},
                {List.of(new TagDto(2L, "hello")),true},
                {List.of(new TagDto(4L, "|_=_|")),false},
                {List.of(new TagDto(1L, "tag3")),false}
        };
    }

    @ParameterizedTest
    @MethodSource("tagValues")
    void isTagListValid(List<TagDto> tag,boolean expected) {
        boolean actual = validator.isTagListValid(tag);
        assertEquals(actual,expected);

    }

    @Test
    void isTagListValidWithValidTagList() {
        List<TagDto> validTagList = List.of(TagDto.builder().id(0L).name("NameOne").build()
                , TagDto.builder().id(0L).name("NameTwo").build()
                , TagDto.builder().id(0L).name("NameThree").build());
        boolean condition = validator.isTagListValid(validTagList);
        assertTrue(condition);
    }

    @Test
    void isTagNameListValidReturnsFalseWithEmptyTagList() {
        List<TagDto> emptyTagList = List.of();
        boolean condition = validator.isTagListValid(emptyTagList);
        assertFalse(condition);
    }

    @Test
    void isStrongTagListValidReturnsFalseWithNullTagList() {
        List<TagDto> nullTagList = null;
        boolean condition = validator.isTagListValid(nullTagList);
        assertFalse(condition);
    }

    @Test
    void isTagNameListValidReturnsFalseWithNullTagInList() {
        List<TagDto> tagListWithNullTag = new ArrayList<>();
        tagListWithNullTag.add(TagDto.builder().id(0L).name("Name").build());
        tagListWithNullTag.add(null);
        boolean condition = validator.isTagListValid(tagListWithNullTag);
        assertFalse(condition);
    }

    @Test
    void isStrongTagNameListValidReturnsFalseWithInvalidTagInList() {
        List<TagDto> tagListWithInvalidTag = List.of(TagDto.builder().id(0L).name("Name#$_!+").build()
                , TagDto.builder().id(0L).name("secondName").build());
        boolean condition = validator.isTagListValid(tagListWithInvalidTag);
        assertFalse(condition);
    }

    @Test
    void isAttributeDtoValid() {
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @Test
    void isRequestOrderDataValidReturnsFalseWithInvalidCertificateParam() {
        orderDto.setCertificateIdList(new ArrayList<>());
        orderDto.getCertificateIdList().add(null);
        boolean condition = validator.isRequestOrderDataValid(orderDto);
        assertFalse(condition);
    }

    @Test
    void isPageDataValid() {
        boolean condition = validator.isPageDataValid(pageable);
        assertTrue(condition);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1, 35", "146, -1"})
    void isPageDataValidReturnsFalseWithInvalidParams(Integer size, Integer page) {
        pageable.setSize(size);
        pageable.setPage(page);
        boolean condition = validator.isPageDataValid(pageable);
        assertFalse(condition);
    }

    @Test
    void isPageExists() {
        boolean condition = validator.isPageExists(pageable,20L);
        assertTrue(condition);
    }


    private static Object[][] description(){
        return new Object[][] {
                {"description",true},
                {"descr1",false},
                {"name++C+",false},
                {"hello?0_/",false}
        };
    }

    @ParameterizedTest
    @MethodSource("description")
    void isDescriptionValid(String description,boolean expected) {
        boolean condition = validator.isDescriptionValid(description);
        assertEquals(condition,expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"43.56", "51", "02", "443", "100", "0.12"})
    void isPriceValidReturnsTrueWithInsertedValidPrice(String strPrice) {
        BigDecimal validPrice = new BigDecimal(strPrice);
        boolean condition = validator.isPriceValid(validPrice);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"5545.34", "5", "0", "450", "10000", "0.9"})
    void isSoftPriceValidReturnsTrueWithValidPrice(String strPrice) {
        BigDecimal validPrice = strPrice != null ? new BigDecimal(strPrice) : null;
        boolean condition = validator.isPriceValid(validPrice);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8, 9})
    void isDurationValid(int validDuration) {
        boolean condition = validator.isDurationValid(validDuration);
        assertTrue(condition);
    }



    @ParameterizedTest
    @MethodSource("nameValues")
    void isNameValid(String name,boolean expected) {
        boolean actual = validator.isNameValid(name);
        assertEquals(expected,actual);
    }

    private static Object[][] nameValues(){
        return new Object[][] {
                {"taggg",true},
                {"user",true},
                {"name++C+",false},
                {"hello?0_/",false}
        };
    }

}
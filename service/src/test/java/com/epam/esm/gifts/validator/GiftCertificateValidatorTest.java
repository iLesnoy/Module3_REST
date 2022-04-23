package com.epam.esm.gifts.validator;

import com.epam.esm.gifts.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.gifts.validator.GiftCertificateValidator.ActionType.INSERT;
import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {

    private GiftCertificateValidator validator;

    @BeforeEach
    void setUp(){
        validator = new GiftCertificateValidator();
    }

    @ParameterizedTest
    @MethodSource("tagValues")
    void isTagListValid(List<TagDto> tag,boolean expected) {
        boolean actual = validator.isTagListValid(tag);
        assertEquals(actual,expected);

    }

    /*    private static Object[][] tagValues(){
            return new Object[][] {
                    {List.of(new TagDto(7, "lenovo")),true},
                    {List.of(new TagDto(2, "hello")),true},
                    {List.of(new TagDto(4, "|_=_|")),false},
                    {List.of(new TagDto(1, "tag3")),false}
            };
        }

    @ParameterizedTest
    @MethodSource("nameValues")
    void isNameValid(String name,boolean expected) {
        boolean actual =validator.isNameValid(name, INSERT);
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

    @Test
    void isNameValidReturnsFalseWithInsertedMoreThen65SymbolsName() {
        String moreThenMaxSize= "N".repeat(66);
        boolean condition = validator.isNameValid(moreThenMaxSize, INSERT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"gift1", "gift-hello", "helloWorld", "привет"})
    void isNameValidReturnsTrueInsertedValidParam(String validName) {
        boolean condition = validator.isNameValid(validName, GiftCertificateValidator.ActionType.UPDATE);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"(hello-)", "rewt", "1234", "Ne&Stl", "<>?_)!@#", "'?'"})
    void isNameValidReturnsFalseWithInsertedInvalidParams(String invalidName) {
        boolean condition = validator.isNameValid(invalidName, INSERT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"cell", "simple+tag", "name", "name-"})
    void isNameValidReturnsTrueWithUpdatedValidParams(String validName) {
        boolean condition = validator.isNameValid(validName, GiftCertificateValidator.ActionType.UPDATE);
        assertTrue(condition);
    }


    @ParameterizedTest
    @ValueSource(strings = {"Description", "Description1", "Description2!", "Description;", "Description+", "Description-"})
    void isDescriptionValid(String validDescription) {
        boolean condition = validator.isDescriptionValid(validDescription, INSERT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"43.56", "51", "02", "443", "1-00", "0.12"})
    void isPriceValidReturnsTrueWithInsertedValidPrice(String strPrice) {
        BigDecimal validPrice = new BigDecimal(strPrice);
        boolean condition = validator.isPriceValid(validPrice, INSERT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"+14", "544554.55"})
    void isPriceValidReturnsFalseWithInsertedValidPrice(String strPrice) {
        BigDecimal validPrice = strPrice != null ? new BigDecimal(strPrice) : null;
        boolean condition = validator.isPriceValid(validPrice, INSERT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8, 9})
    void isDurationValid(int validDuration) {
        boolean condition = validator.isDurationValid(validDuration, INSERT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"asc", "", "ASC", "DESC", "dec", "asC"})
    void isOrderSortValidReturnsTrue(String orderSort) {
        boolean condition = validator.isSortOrderValid(orderSort);
        assertTrue(condition);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"hello", "DESC", " ", "desC ", " Asc"})
    void isOrderSortValidReturnsFalseWithInvalidArg(String orderSort) {
        boolean condition = validator.isSortOrderValid(orderSort);
        assertFalse(condition);
    }*/
}
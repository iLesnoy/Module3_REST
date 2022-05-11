package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagConverterTest {

    private TagConverter tagConverter;
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    public void setUp() {
        tagConverter = new TagConverter();
        tag = Tag.builder().id(1L).name("name").build();
        tagDto = TagDto.builder().id(1L).name("name").build();
    }

    @Test
    void dtoToTag() {
        Tag actual = tagConverter.dtoToTag(tagDto);
        assertEquals(tag,actual);
    }

    @Test
    void tagToDto() {
        TagDto actual = tagConverter.tagToDto(tag);
        assertEquals(tagDto,actual);
    }
}
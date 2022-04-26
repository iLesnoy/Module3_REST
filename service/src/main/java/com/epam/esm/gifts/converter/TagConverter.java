package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {

    public static Tag dtoToTag(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    public static TagDto tagToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}

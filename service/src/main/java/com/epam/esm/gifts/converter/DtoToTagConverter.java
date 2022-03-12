package com.epam.esm.gifts.converter;

import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTagConverter implements Converter<TagDto, Tag> {

    @Override
    public Tag convert(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}

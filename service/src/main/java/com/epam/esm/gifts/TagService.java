package com.epam.esm.gifts;


import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;

public interface TagService {

    TagDto create(TagDto tagDto);

    TagDto update(Long id,TagDto tagDto);

    TagDto findById(Long id);

    TagDto delete(Long id);

    TagDto findOrCreateTag(TagDto tagDto);
}

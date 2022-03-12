package com.epam.esm.gifts;


import com.epam.esm.gifts.dto.TagDto;

public interface TagService {

    TagDto create(TagDto tagDto);

    TagDto update(Long id,TagDto tagDto);

    TagDto findById(Long id);

    TagDto delete(Long id);
}

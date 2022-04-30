package com.epam.esm.gifts;


import com.epam.esm.gifts.dto.TagDto;

public interface TagService extends BaseService<TagDto> {

    TagDto create(TagDto tagDto);

    TagDto update(Long id,TagDto tagDto);

    TagDto findById(Long id);

    void delete(Long id);

    TagDto findOrCreateTag(TagDto tagDto);

}

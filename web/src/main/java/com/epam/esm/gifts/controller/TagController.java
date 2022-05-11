package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.TagService;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.hateaos.HateoasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public TagController(TagService tagService, HateoasBuilder hateoasBuilder) {
        this.tagService = tagService;
        this.hateoasBuilder = hateoasBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
         tagService.delete(id);
    }

    @GetMapping
    public CustomPage<TagDto> findAll(@PathVariable CustomPageable pageable) {
        CustomPage<TagDto> customPage = tagService.findAll(pageable);
        customPage.getContent().forEach(hateoasBuilder::setLinks);
        return customPage;
    }

    /*@PutMapping
    public TagDto update(@RequestBody TagDto tagDto) {
        return tagService.update(tagDto.getId(),tagDto);
    }*/
}
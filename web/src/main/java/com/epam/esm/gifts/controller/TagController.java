package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.TagService;
import com.epam.esm.gifts.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
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

    @GetMapping
    public TagDto findOrCreateByName(@RequestBody TagDto tagDto) {
        return tagService.findOrCreateTag(tagDto);
    }

    @PutMapping
    public TagDto update(@RequestBody TagDto tagDto) {
        return tagService.update(tagDto.getId(),tagDto);
    }

    @DeleteMapping("/{id}")
    public TagDto deleteTag(@PathVariable Long id) {
        return tagService.delete(id);
    }
}
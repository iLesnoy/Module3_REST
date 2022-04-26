package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import com.epam.esm.gifts.dto.GiftCertificateAttributeDto;
import com.epam.esm.gifts.hateaos.HateaosBuilder;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, HateaosBuilder hateaosBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto insert(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.create(giftCertificateDto);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable Long id,
                                     @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(id,giftCertificateDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @GetMapping
    public CustomPage<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attribute, CustomPageable pageable) {
        CustomPage<GiftCertificateDto> page = giftCertificateService.searchByParameters(attribute, pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @DeleteMapping("deleteTags/{id}")
    private boolean deleteAllTagsFromGiftById(@PathVariable Long id){
        return false;
    }
}

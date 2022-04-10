package com.epam.esm.gifts.controller;

import com.epam.esm.gifts.GiftCertificateService;
import com.epam.esm.gifts.dto.GiftCertificateDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
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
    public List<GiftCertificateDto> findByAttributes(@RequestParam(required = false, name = "tagName") String tagName,
                                                     @RequestParam(required = false, name = "searchPart") String searchPart,
                                                     @RequestParam(required = false, name = "description") String description,
                                                     @RequestParam(required = false, name = "sortingFields") List<String> sortingFields,
                                                     @RequestParam(required = false, name = "orderSort") String orderSort) {
        return giftCertificateService.searchByParameters(tagName, searchPart, description, sortingFields, orderSort);
    }

    @DeleteMapping("/{id}")
    public GiftCertificateDto deleteById(@PathVariable Long id) {
        return giftCertificateService.delete(id);
    }

    @DeleteMapping("deleteTags/{id}")
    private boolean deleteAllTagsFromGiftById(@PathVariable Long id){
        return giftCertificateService.deleteAllTagsFromCertificate(id);
    }
}

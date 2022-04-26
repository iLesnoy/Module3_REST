package com.epam.esm.gifts.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateAttribute {

    private List<String> tagNameList;
    private String searchPart;
    private String orderSort;
    private List<String> sortingFieldList;
}
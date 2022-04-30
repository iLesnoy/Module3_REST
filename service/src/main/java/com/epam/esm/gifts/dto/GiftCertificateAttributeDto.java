package com.epam.esm.gifts.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateAttributeDto {

    private List<String> tagNameList;
    private String searchPart;
    private String orderSort;
    private List<String> sortingFieldList;
}
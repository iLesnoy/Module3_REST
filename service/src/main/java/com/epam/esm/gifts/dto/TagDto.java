package com.epam.esm.gifts.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class TagDto implements Serializable {
    private final Long id;
    private final String name;
}

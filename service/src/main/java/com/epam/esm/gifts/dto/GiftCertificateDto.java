package com.epam.esm.gifts.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"id", "name", "description", "price", "duration", "create-date", "last-update-date", "tags"})
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("create-date")
    private LocalDateTime createDate;
    @JsonProperty("last-update-date")
    private LocalDateTime lastUpdateDate;
    @JsonProperty("tags")
    private List<TagDto> tagDtoList;
}

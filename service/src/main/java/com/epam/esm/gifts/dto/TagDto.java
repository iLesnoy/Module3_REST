package com.epam.esm.gifts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name"})
public class TagDto extends RepresentationModel<TagDto> {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
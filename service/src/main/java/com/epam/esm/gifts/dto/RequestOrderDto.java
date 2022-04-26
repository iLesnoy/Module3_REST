package com.epam.esm.gifts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestOrderDto {
    @JsonProperty("user-id")
    private Long userId;
    @JsonProperty("certificate's-id")
    private List<Long> certificateIdList;
}

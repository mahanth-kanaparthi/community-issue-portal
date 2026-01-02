package com.mk.ipapp.dto.region;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class RegionUpdateRequest {
    private String name;
    private Long regionCode;
}

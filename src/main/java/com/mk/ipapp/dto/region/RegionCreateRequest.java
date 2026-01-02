package com.mk.ipapp.dto.region;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionCreateRequest {
    private String name;
    private Long regionCode;
}

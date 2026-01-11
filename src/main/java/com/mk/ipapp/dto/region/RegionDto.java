package com.mk.ipapp.dto.region;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class RegionDto {

    private Long id;
    private String name;
    private Long regionCode;
}

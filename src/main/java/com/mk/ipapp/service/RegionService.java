package com.mk.ipapp.service;

import com.mk.ipapp.dto.region.RegionCreateRequest;
import com.mk.ipapp.dto.region.RegionUpdateRequest;
import com.mk.ipapp.entity.Region;

import java.util.List;

public interface RegionService {

    Region createRegion(RegionCreateRequest request);

    Region updateRegion(RegionUpdateRequest request);

    void deleteRegion(Long regionId);

    Region getByRegionCode(Long regionCode);

    List<Region> getAllRegion();

}

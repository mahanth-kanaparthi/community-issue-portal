package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.region.RegionCreateRequest;
import com.mk.ipapp.dto.region.RegionUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.repository.RegionRepository;
import com.mk.ipapp.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {


    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }

    @Override
    public Region createRegion(RegionCreateRequest request) {

        if(regionRepository.existsByRegionCode(request.getRegionCode())){
            throw new RuntimeException("Region already exists!");
        }

        Region region = Region.builder()
                .name(request.getName())
                .regionCode(request.getRegionCode())
                .build();
        regionRepository.save(region);

        return regionRepository.findByRegionCode(request.getRegionCode()).orElseThrow(
                () -> new RuntimeException("Region not found"));
    }

    @Override
    public Region updateRegion(RegionUpdateRequest request) {

        Region region = regionRepository.findByRegionCode(request.getRegionCode()).orElseThrow(
                () -> new RuntimeException("Region not found")
        );

        region.setName(request.getName());
        regionRepository.save(region);

        return region;
    }

    @Override
    public void deleteRegion(Long regionCode) {

        Region region = regionRepository.findByRegionCode(regionCode).orElseThrow(
                () -> new RuntimeException("Region not found")
        );

        regionRepository.deleteById(region.getId());

    }

    @Override
    public Region getByRegionCode(Long regionCode) {

        return regionRepository.findByRegionCode(regionCode).orElseThrow(
                () -> new RuntimeException("Region not found")
        );
    }

    @Override
    public List<Region> getAllRegion() {
        return regionRepository.findAll();
    }
}

package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.region.RegionCreateRequest;
import com.mk.ipapp.dto.region.RegionUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.repository.RegionRepository;
import com.mk.ipapp.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
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
                .createdAt(java.time.LocalDateTime.now())
                .build();
        return regionRepository.save(region);
    }

    @Override
    public Region updateRegion(Long id, RegionUpdateRequest request) {

        Region region = regionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Region not found")
        );

        region.setName(request.getName());
        return regionRepository.save(region);
    }

    @Override
    public void deleteRegion(Long regionCode) {

        Region region = regionRepository.findByRegionCode(regionCode).orElseThrow(
                () -> new RuntimeException("Region not found")
        );

        // Check if users are assigned to this region before deleting
        if (region.getUsers() != null && !region.getUsers().isEmpty()) {
            throw new RuntimeException("Cannot delete region: Users are still assigned to it.");
        }

        regionRepository.delete(region);

    }

    @Override
    @Transactional(readOnly = true)
    public Region getByRegionCode(Long regionCode) {

        return regionRepository.findByRegionCode(regionCode).orElseThrow(
                () -> new RuntimeException("Region not found")
        );
    }

    @Override
    public Boolean existsByRegionCode(Long regionCode){

        return regionRepository.existsByRegionCode(regionCode);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }
}

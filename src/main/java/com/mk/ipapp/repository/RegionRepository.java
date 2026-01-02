package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByName(String name);

    Optional<Region> findByRegionCode(Long regionCode);

    Boolean existsByRegionCode(Long regionCode);
}

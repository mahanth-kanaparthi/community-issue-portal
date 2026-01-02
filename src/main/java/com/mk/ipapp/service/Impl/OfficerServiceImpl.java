package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.UserMapper;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.officer.OfficerCreateRequest;
import com.mk.ipapp.dto.officer.OfficerUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;
import com.mk.ipapp.repository.RegionRepository;
import com.mk.ipapp.repository.UserRepository;
import com.mk.ipapp.service.OfficerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficerServiceImpl implements OfficerService {

    UserRepository userRepository;
    RegionRepository regionRepository;
    UserMapper userMapper;

    public OfficerServiceImpl(UserRepository userRepository, RegionRepository regionRepository,
                           UserMapper userUserMapper){
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.userMapper = userUserMapper;
    }


    @Override
    public UserSummary createOfficer(OfficerCreateRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists!");
        }
        Region region = regionRepository.findByRegionCode(request.getRegionCode()).orElseThrow(
                () ->  new RuntimeException("Region not found")
        );
        User user = userMapper.requestToUser(request, region);

        return userMapper.userToUserSummary(userRepository.save(user));

    }

    @Override
    public UserSummary updateOfficer(OfficerUpdateRequest request) {
        User officer = userRepository.findById(request.getId()).orElseThrow(
                () -> new RuntimeException("Officer not found!")
        );

        if(request.getFullName() != null){
            officer.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            officer.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            officer.setPhone(request.getPhone());
        }
        if (request.getRegionCode() != null) {
            Region region = regionRepository.findByRegionCode(request.getRegionCode())
                    .orElseThrow(() -> new RuntimeException("Region not found"));
            officer.setRegion(region);
        }
        if (request.getActive() != null) {
            officer.setActive(request.getActive());
        }

        return userMapper.userToUserSummary(userRepository.save(officer));
    }

    @Override
    public void deactivateOfficer(Long officerId) {
        User officer = userRepository.findById(officerId)
                .orElseThrow(() -> new RuntimeException("Officer not found"));
        officer.setActive(false);
        userRepository.save(officer);
    }

    @Override
    public List<UserSummary> listOfficers(Long regionCode) {
        Region region = regionRepository.findByRegionCode(regionCode).orElseThrow(
                () ->  new RuntimeException("Region not found")
        );
        List<User> officersInRegion = userRepository.findByRegionAndRole(region, Role.ROLE_OFFICER);

        return officersInRegion.stream()
                .map(officer -> userMapper.userToUserSummary(officer))
                .toList();
    }
}

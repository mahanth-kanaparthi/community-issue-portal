package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.UserMapper;
import com.mk.ipapp.dto.UserRegisterRequest;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.UserUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;
import com.mk.ipapp.repository.RegionRepository;
import com.mk.ipapp.repository.UserRepository;
import com.mk.ipapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    public UserServiceImpl(UserRepository userRepository, RegionRepository regionRepository){
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public UserSummary registerUser(UserRegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists!");
        }
        Region region = regionRepository.findByRegionCode(request.getRegionCode()).orElseThrow(
                () ->  new RuntimeException("Region not found")
        );
        User user = UserMapper.toUser(request, region);

        //saving user & returning user summary obj
        return UserMapper.toUserSummary(userRepository.save(user));
    }

    @Override
    public User getCurrentUser() {
        //String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return null;
    }

    public UserSummary updateUser(Long userId, UserUpdateRequest request){
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new RuntimeException("User not found!")
        );

        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getRegionCode() != null) {
            Region region = regionRepository.findByRegionCode(request.getRegionCode())
                    .orElseThrow(() -> new RuntimeException("Region not found"));
            user.setRegion(region);
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        return UserMapper.toUserSummary(userRepository.save(user));
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getUsersByRegionAndRole(Region region, Role role) {

        return userRepository.findByRegionAndRole(region, role);
    }
}

package com.mk.ipapp.dto;

import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {

//    UserRepository userRepository;
//    RegionRepository regionRepository;

    //returns a new user object without setting a region
    public User requestToUser(UserRegisterRequest request, Region region){
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getP_user() ? Role.ROLE_P_USER : Role.ROLE_USER)
                .phone(request.getPhone())
                .password(request.getPassword())
                .region(region)
                .build();
    }

    // returns a UserSummary object without userId and regionId
    public UserSummary userToUserSummary(User user){
        return UserSummary.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().toString())
                .regionCode(user.getRegion().getRegionCode())
                .build();
    }

    //returns a new user object without setting a region
    public static User toUser(UserRegisterRequest request, Region region){
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getP_user() ? Role.ROLE_P_USER : Role.ROLE_USER)
                .phone(request.getPhone())
                .password(request.getPassword())
                .region(region)
                .build();
    }
    public static User toUser(UserSummary userSummary, Region region){
        Role role = Role.valueOf(userSummary.getRole());
        return User.builder()
                .fullName(userSummary.getFullName())
                .email(userSummary.getEmail())
                .role(role)
                .phone(userSummary.getPhone())
                .active(userSummary.getActive())
                .region(region)
                .build();
    }

    // returns a UserSummary object without userId and regionId
    public static UserSummary toUserSummary(User user){
        return UserSummary.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().toString())
                .regionCode(user.getRegion().getRegionCode())
                .build();
    }

}

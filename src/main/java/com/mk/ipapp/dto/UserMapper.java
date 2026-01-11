package com.mk.ipapp.dto;

import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {

//    UserRepository userRepository;
//    RegionRepository regionRepository;


    public static User toUser(UserRegisterRequest request, Region region){
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getP_user() ? Role.ROLE_P_USER : Role.ROLE_USER)
                .phone(request.getPhone())
                .password(request.getPassword())
                .region(region)
                .active(true) // true for newly created users
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


//    public static User toUser(UserRegisterRequest request, Region region){
//        return User.builder()
//                .fullName(request.getFullName())
//                .email(request.getEmail())
//                .role(request.getP_user() ? Role.ROLE_P_USER : Role.ROLE_USER)
//                .phone(request.getPhone())
//                .password(request.getPassword())
//                .region(region)
//                .build();
//    }
    public static User toUser(UserSummary userSummary, Region region){
        return User.builder()
                .id(userSummary.getId())
                .fullName(userSummary.getFullName())
                .email(userSummary.getEmail())
                .role(Role.valueOf(userSummary.getRole()))
                .phone(userSummary.getPhone())
                .active(userSummary.getActive())
                .region(region)
                .build();
    }

    // returns a UserSummary object without userId and regionId
    public static UserSummary toUserSummary(User user){
        if(user == null) return null;
        return UserSummary.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .active(user.getActive())
                .regionCode(user.getRegion() != null ? user.getRegion().getRegionCode() : null)
                //null safe navigation for a lazy-loaded region
                .build();
    }

}

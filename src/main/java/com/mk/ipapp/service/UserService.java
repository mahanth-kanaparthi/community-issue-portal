package com.mk.ipapp.service;

import com.mk.ipapp.dto.UserRegisterRequest;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.UserUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;

import java.util.List;

public interface UserService {

    UserSummary registerUser(UserRegisterRequest request);

    User getCurrentUser(); // from security context

    UserSummary updateUser(Long userId, UserUpdateRequest request);

    User findById(Long id);

    User findByEmail(String email);

    List<User> getUsersByRegionAndRole(Region region, Role role);

}

package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRegionAndRole(Region region, Role role);

    Boolean existsByEmail(String email);
}

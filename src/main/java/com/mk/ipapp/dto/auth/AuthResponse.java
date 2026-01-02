package com.mk.ipapp.dto.auth;

import com.mk.ipapp.dto.UserSummary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private String accessToken;
    private final String tokenType = "Bearer";
    private long expiresIn;
    private UserSummary user; // own dto
}

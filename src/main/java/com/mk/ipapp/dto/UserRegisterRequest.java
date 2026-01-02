package com.mk.ipapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Boolean p_user;
    private Long regionCode;

}

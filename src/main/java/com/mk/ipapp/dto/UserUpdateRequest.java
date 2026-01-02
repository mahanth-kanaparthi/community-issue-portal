package com.mk.ipapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Data
public class UserUpdateRequest {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Long regionCode;
    private Boolean active;
}

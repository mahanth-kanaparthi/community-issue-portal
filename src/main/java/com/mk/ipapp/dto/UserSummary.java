package com.mk.ipapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserSummary {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private Boolean active;
    private Long regionCode;
}

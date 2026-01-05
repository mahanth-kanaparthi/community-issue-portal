package com.mk.ipapp.dto;

import lombok.*;

@AllArgsConstructor
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

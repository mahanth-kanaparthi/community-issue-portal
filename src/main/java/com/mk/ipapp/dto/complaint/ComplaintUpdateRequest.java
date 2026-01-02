package com.mk.ipapp.dto.complaint;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplaintUpdateRequest {

    private String status;
    private String remark;
}

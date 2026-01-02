package com.mk.ipapp.dto.complaint.history;

import lombok.*;

@Getter @Setter @Builder
public class ComplaintHistoryDto {

    private String status;
    private String remark;
    private String updateBy;
    private String updatedAt;
}

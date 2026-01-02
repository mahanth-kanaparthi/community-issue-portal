package com.mk.ipapp.dto.complaint;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ComplaintSummary {

    private Long id;
    private String complaintCode;
    private String title;
    private String category;
    private String status;
    private String createdAt;
    private String updatedBy;
    private String remark;
}

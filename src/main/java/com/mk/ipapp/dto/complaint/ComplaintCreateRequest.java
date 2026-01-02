package com.mk.ipapp.dto.complaint;

import com.mk.ipapp.enums.ComplaintCategory;
import com.mk.ipapp.enums.ComplaintStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ComplaintCreateRequest {

    private String title;
    private String description;
    private ComplaintCategory category;
    private ComplaintStatus status = ComplaintStatus.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private String remark = "Complaint created";
    private Double latitude;
    private Double longitude;

}

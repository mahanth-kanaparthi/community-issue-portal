package com.mk.ipapp.dto.complaint;

import com.mk.ipapp.dto.complaint.attachment.AttachmentDto;
import com.mk.ipapp.dto.complaint.history.ComplaintHistoryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class ComplaintDetail {

    private Long id;
    private String complaintCode;
    private String title;
    private String description;
    private String category;
    private String status;
    private String regionName;
    private String complaintBy; // hidden in public
    private String assignedOfficerName;
    private String updateBy;
    private String createdAt;
    private String updatedAt;
    private String remark;
    private AttachmentDto attachment;
    private List<ComplaintHistoryDto> history;
}

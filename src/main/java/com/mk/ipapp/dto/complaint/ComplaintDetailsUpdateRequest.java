package com.mk.ipapp.dto.complaint;

import com.mk.ipapp.dto.complaint.attachment.AttachmentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ComplaintDetailsUpdateRequest {

    private Long id;
    private String complaintCode;
    private String title;
    private String description;
    private String category;
    private String status;
    private String regionName;
    private String remark;
    private AttachmentDto attachment;
}

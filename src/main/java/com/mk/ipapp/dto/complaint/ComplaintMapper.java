package com.mk.ipapp.dto.complaint;

import com.mk.ipapp.dto.complaint.attachment.AttachmentDto;
import com.mk.ipapp.dto.complaint.attachment.AttachmentMapper;
import com.mk.ipapp.dto.complaint.history.ComplaintHistoryDto;
import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.repository.AttachmentRepository;
import com.mk.ipapp.repository.ComplaintHistoryRepository;
import com.mk.ipapp.service.AttachmentService;
import com.mk.ipapp.service.ComplaintHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintMapper {


    private final AttachmentService attachmentService;
    private final ComplaintHistoryService historyService;

    public ComplaintMapper(AttachmentService attachmentService, ComplaintHistoryService historyService){
        this.attachmentService = attachmentService;
        this.historyService = historyService;
    }
    public ComplaintDetail toComplaintDetail(Complaint complaint){
        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(complaint);


        ComplaintDetail detail = ComplaintDetail.builder()
                .id(complaint.getId())
                .complaintCode(complaint.getComplaintCode())
                .title(complaint.getTitle())
                .description((complaint.getDescription()))
                .category(complaint.getCategory().toString())
                .status(complaint.getStatus().toString())
                .complaintBy(complaint.getComplaintBy().getFullName())
                .assignedOfficerName(complaint.getAssignedOfficer().getFullName())
                .createdAt(complaint.getCreatedAt().toString())
                .updatedAt(complaint.getUpdateAt().toString())
                .build();

        detail.setHistory(
                historyList.stream()
                        .map(history -> {
                            return ComplaintHistoryDto.builder()
                                    .status(history.getStatus().toString())
                            .remark(history.getRemark())
                            .updatedAt(history.getUpdatedAt().toString())
                            .updateBy(
                                    history.getUpdateBy() != null ? history.getUpdateBy().getFullName() : null
                            ).build();
                        }).toList()
        );


        detail.setAttachment(
                AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(complaint))
        );

        return detail;
    }

    public static ComplaintSummary toComplaintSummary(Complaint complaint){

        return ComplaintSummary.builder()
                .id(complaint.getId())
                .complaintCode(complaint.getComplaintCode())
                .title(complaint.getTitle())
                .category(complaint.getCategory().toString())
                .status(complaint.getStatus().toString())
                .createdAt(complaint.getCreatedAt().toString())
                .remark(complaint.getRemark())
                .updatedBy(complaint.getUpdatedBy() != null ? complaint.getUpdatedBy().getFullName(): null)
                .build();
    }
}

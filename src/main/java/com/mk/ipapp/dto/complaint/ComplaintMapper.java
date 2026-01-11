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

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComplaintMapper {

    public static ComplaintDetail toComplaintDetail(Complaint complaint,
                                                    List<ComplaintHistory> historyList, AttachmentDto attachment){
        if(complaint == null) return null;

        return ComplaintDetail.builder()
                .id(complaint.getId())
                .complaintCode(complaint.getComplaintCode())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory().name())
                .status(complaint.getStatus().name())
                .regionName(complaint.getRegion() != null ? complaint.getRegion().getName() : null)
                .complaintBy(complaint.getComplaintBy() != null ? complaint.getComplaintBy().getFullName() : "Unknown")
                .assignedOfficerName(complaint.getAssignedOfficer() != null ? complaint.getAssignedOfficer().getFullName() : "Unassigned")
                .actionBy(complaint.getComplaintBy() != null ? complaint.getComplaintBy().getFullName() : "Unknown")
                .createdAt(complaint.getCreatedAt() != null ? complaint.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null)
                .updatedAt(complaint.getUpdatedAt() != null ? complaint.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")):null)
                .remark(complaint.getRemark())
                .history(mapHistory(historyList))
                .attachment(attachment)
                .build();

    }

    public static ComplaintSummary toComplaintSummary(Complaint complaint){

        return ComplaintSummary.builder()
                .id(complaint.getId())
                .complaintCode(complaint.getComplaintCode())
                .title(complaint.getTitle())
                .category(complaint.getCategory().toString())
                .status(complaint.getStatus().toString())
                .createdAt(complaint.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(complaint.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .remark(complaint.getRemark())
                .actionBy(complaint.getActionBy() != null ? complaint.getActionBy().getFullName(): null)
                .build();
    }
    private static List<ComplaintHistoryDto> mapHistory(List<ComplaintHistory> historyList){
        if(historyList == null) return List.of();
        return historyList.stream()
                .map(history -> ComplaintHistoryDto.builder()
                        .status(history.getStatus().name())
                        .remark(history.getRemark())
                        .updatedAt(history.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .updateBy(history.getActionBy() != null ? history.getActionBy().getFullName() : "System")
                        .build())
                .toList();
    }
}

package com.mk.ipapp.service;

import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.complaint.ComplaintCreateRequest;
import com.mk.ipapp.dto.complaint.ComplaintDetail;
import com.mk.ipapp.dto.complaint.ComplaintUpdateRequest;
import com.mk.ipapp.dto.complaint.ComplaintSummary;
import com.mk.ipapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComplaintService {

    ComplaintDetail CreateComplaint(ComplaintCreateRequest request, UserSummary citizen);

    Page<ComplaintSummary> getComplaintsForUser(User citizen, String status, Pageable pageable);

    ComplaintDetail getComplaintDetailForUser(Long complaintId, UserSummary citizen);

    ComplaintDetail getComplaintDetailByComplaintCode(String complaintCode); // public tracking

    Page<ComplaintSummary> getComplaintsForOfficer(User officer, List<String> categories, List<String> statuses, Pageable pageable);

    ComplaintDetail getComplaintDetailForOfficer(Long complaintId, UserSummary officer);

    ComplaintDetail updateComplaintStatus(Long complaintId, UserSummary officer, ComplaintUpdateRequest request);

    //Admin

    Page<ComplaintSummary> getComplaintForAdmin(Long regionId, Long officerId, List<String> status, String fromDate, String toDate, Pageable pageable);

    ComplaintDetail assignComplaintToOfficer(Long complaintId);




}

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

    // --Citizen(normal user)--
    ComplaintDetail createComplaint(ComplaintCreateRequest request, UserSummary citizen);

    Page<ComplaintSummary> getComplaintsForUser(UserSummary user, String status, Pageable pageable);

    ComplaintDetail getComplaintDetailForUser(Long id, UserSummary citizen);

    // Enhancement: Update Details (Officer modifies description/categories, etc.)
    ComplaintDetail updateComplaintDetails(Long id, UserSummary user, ComplaintUpdateRequest request);
    

    // --Officer--
    Page<ComplaintSummary> getComplaintsForOfficer(User officer, List<String> categories, List<String> statuses, Pageable pageable);

    ComplaintDetail getComplaintDetailForOfficer(Long id, UserSummary officer);

    ComplaintDetail updateComplaintStatus(Long id, UserSummary officer, ComplaintUpdateRequest request);

    // --Admin--

    Page<ComplaintSummary> getComplaintForAdmin(Long id, Long officerId, List<String> status, String fromDate, String toDate, Pageable pageable);

    ComplaintDetail assignComplaintToOfficer(Long id);

    // added: admin assign complaint to specific officer.
    //ComplaintDetail assignComplaintToOfficer(Long complaintId, Long officerId);

    ComplaintDetail getComplaintDetailByComplaintCode(String complaintCode); // public tracking


}

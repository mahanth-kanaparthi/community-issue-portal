package com.mk.ipapp.controller;

import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.complaint.ComplaintDetail;
import com.mk.ipapp.dto.complaint.ComplaintDetailsUpdateRequest;
import com.mk.ipapp.dto.complaint.ComplaintSummary;
import com.mk.ipapp.service.ComplaintService;
import com.mk.ipapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/officer")
@RequiredArgsConstructor
//@PreAuthorize(hasRole('OFFICER'))
public class OfficerController {

    private final ComplaintService complaintService;
    private final UserService userService;

    // Retrieve assigned complaints (with filters)

    @GetMapping
    public ResponseEntity<Page<ComplaintSummary>> getAssignedComplaints(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> statuses,
            Pageable pageable){
        UserSummary currentOfficer = userService.getCurrentUser();
        return ResponseEntity.ok(complaintService.getComplaintsForOfficer(currentOfficer, categories, statuses, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDetail> getComplaintDetail(@PathVariable Long id){
        UserSummary currentOfficer = userService.getCurrentUser();
        return ResponseEntity.ok(complaintService.getComplaintDetail(id, currentOfficer));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<ComplaintDetail> updateComplaintDetails(
            @PathVariable Long id,
            @RequestBody ComplaintDetailsUpdateRequest request
    ){
        UserSummary currentOfficer = userService.getCurrentUser();
        return ResponseEntity.ok(complaintService.updateComplaintDetails(id, currentOfficer, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ComplaintDetail> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam (required = false) String remark
    ){
        UserSummary currentOfficer = userService.getCurrentUser();
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, currentOfficer, status, remark));
    }



}
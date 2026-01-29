package com.mk.ipapp.controller;

import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.complaint.ComplaintDetail;
import com.mk.ipapp.dto.complaint.ComplaintSummary;
import com.mk.ipapp.dto.complaint.ComplaintUpdateRequest;
import com.mk.ipapp.dto.officer.OfficerCreateRequest;
import com.mk.ipapp.dto.officer.OfficerStatusUpdateRequest;
import com.mk.ipapp.dto.officer.OfficerUpdateRequest;
import com.mk.ipapp.dto.region.RegionCreateRequest;
import com.mk.ipapp.dto.region.RegionUpdateRequest;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.service.ComplaintService;
import com.mk.ipapp.service.OfficerService;
import com.mk.ipapp.service.RegionService;
import com.mk.ipapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final OfficerService officerService;
    private final ComplaintService complaintService;
    private final RegionService regionService;


    // -- Region --
    @PostMapping("/regions")
    public ResponseEntity<Region> createRegion(@RequestBody RegionCreateRequest request) {
        return ResponseEntity.ok(regionService.createRegion(request));
    }

    @PutMapping("/regions/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Long id,
                               @RequestBody RegionUpdateRequest request) {
        return ResponseEntity.ok(regionService.updateRegion(id, request));
    }

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> listRegions() {
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @DeleteMapping("/regions/{id}")
    public void deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
    }

    // -- Officer --

    @PostMapping("/officers/new")
    public ResponseEntity<UserSummary> createOfficer(@RequestBody OfficerCreateRequest request) {
        return ResponseEntity.ok(officerService.createOfficer(request));
    }

    @PutMapping("/officers/{id}")
    public ResponseEntity<UserSummary> updateOfficer(@PathVariable Long id,
                                     @RequestBody OfficerUpdateRequest request) {
        return ResponseEntity.ok(officerService.updateOfficer(id, request));
    }

    @PatchMapping("/officers/{id}/status")
    public void changeOfficerStatus(@PathVariable Long id,
                                    @RequestBody OfficerStatusUpdateRequest request) {
        if (request.active() != null && !request.active()) {
            officerService.deactivateOfficer(id);
        } else if (request.active() != null && request.active()) {
            officerService.activateOfficer(id);
        }
    }

    @GetMapping("/officers")
    public List<UserSummary> listOfficers(@RequestParam(required = false) Long regionId) {
        return officerService.listOfficers(regionId);
    }

    // Complaints (admin view)
    @GetMapping("/complaints")
    public Page<ComplaintSummary> listComplaints(
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long officerId,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return complaintService.getComplaintsForAdmin(regionId, officerId, status, fromDate, toDate, pageable);
    }

    @PutMapping("/complaints/{id}/assign")
    public ComplaintDetail assignComplaint(@PathVariable Long id,
                                           @RequestBody Map<String, Long> request) {
        Long officerId = request.get("officerId");
        UserSummary admin = userService.getCurrentUser();
        return complaintService.assignComplaintToOfficer(id, officerId, admin);
    }

    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<ComplaintDetail> updateStatusAsAdmin(@PathVariable Long id,
                                                               @RequestBody ComplaintUpdateRequest request) {
        UserSummary admin = userService.getCurrentUser();
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, admin, request));
    }
}

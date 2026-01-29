package com.mk.ipapp.controller;

import com.mk.ipapp.dto.UserUpdateRequest;
import com.mk.ipapp.dto.complaint.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.service.ComplaintService;
import com.mk.ipapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final ComplaintService complaintService;
    private final UserService userService;



    // -- create complaint --
    @PostMapping("/complaints")
    public ResponseEntity<ComplaintDetail> createComplaint(@RequestBody @Validated ComplaintCreateRequest request){

        UserSummary currentUser = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(complaintService.createComplaint(request, currentUser));
    }

    @GetMapping("/complaints")
    public ResponseEntity<Page<ComplaintSummary>> getMyComplaints(
        @RequestParam(required = false) String status,
        org.springframework.data.domain.Pageable pageable) {

            UserSummary currentUser = userService.getCurrentUser();

            return ResponseEntity.ok(complaintService.getComplaintsForUser(currentUser, status, pageable));
        }

    @GetMapping("/complaints/{id}")
    public ResponseEntity<ComplaintDetail> getComplaintDetail(@PathVariable Long id){

        UserSummary currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(complaintService.getComplaintDetailForUser(id, currentUser));
    }

    @PutMapping("/complaints/{id}")
    public ResponseEntity<ComplaintDetail> updateComplaintDetails( @PathVariable Long id, 
        @RequestBody ComplaintDetailsUpdateRequest request){

            UserSummary currentUser = userService.getCurrentUser();

            return ResponseEntity.ok(complaintService.updateComplaintDetails(id, currentUser, request));
    }

    @PostMapping("/profile")
    public ResponseEntity<UserSummary> updateUserDetails(@RequestBody UserUpdateRequest request){
        UserSummary currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userService.updateUser(currentUser.getId(), request));
    }
}

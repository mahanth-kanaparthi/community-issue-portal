package com.mk.ipapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.ipapp.dto.complaint.ComplaintDetail;
import com.mk.ipapp.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    
    private final ComplaintService complaintService;


    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome to Community Issue Portal");
    }

    @GetMapping("/track/{complaintCode}")
    public ResponseEntity<ComplaintDetail> trackComplaint(@PathVariable String complaintCode){

        return ResponseEntity.ok(complaintService.getComplaintDetailByComplaintCode(complaintCode));
    }


}

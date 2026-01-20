package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.UserMapper;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.complaint.*;
import com.mk.ipapp.dto.complaint.attachment.AttachmentDto;
import com.mk.ipapp.dto.complaint.attachment.AttachmentMapper;
import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.ComplaintCategory;
import com.mk.ipapp.enums.ComplaintStatus;
import com.mk.ipapp.enums.Role;
import com.mk.ipapp.repository.*;
import com.mk.ipapp.service.*;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintServiceImpl implements ComplaintService {


    private final AttachmentService attachmentService;
    private final ComplaintRepository complaintRepository;
    private final UserService userService;
    private final RegionService regionService;
    private final ComplaintHistoryService historyService;


    @Override
    public ComplaintDetail createComplaint(ComplaintCreateRequest request, UserSummary user) {

        Region region = regionService.getByRegionCode(user.getRegionCode());

        Role role = Role.valueOf(user.getRole());

        Complaint complaint = Complaint.builder()
                .complaintCode(generateComplaintCode(role))
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(ComplaintStatus.PENDING)
                .complaintBy(UserMapper.toUser(user,region))
                .region(region)
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Complaint saved = complaintRepository.save(complaint);

        // saves history automatically by jpa listener

        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(saved);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(saved));

        return ComplaintMapper.toComplaintDetail(saved,historyList, attachment);
    }

    @Override
    public Page<ComplaintSummary> getComplaintsForUser(UserSummary user, String status, Pageable pageable) {

        Region region = regionService.getByRegionCode(user.getRegionCode());
        User currentUser = UserMapper.toUser(user, region);
        Page<Complaint> page;

        if(status != null){
            ComplaintStatus s = ComplaintStatus.valueOf(status);
            page = complaintRepository.findByComplaintByAndStatus(currentUser, s, pageable);
        }else{
            page = complaintRepository.findByComplaintBy(currentUser, pageable);
        }

        return page.map(ComplaintMapper::toComplaintSummary);

    }

    @Override
    public ComplaintDetail getComplaintDetailForUser(Long id, UserSummary user) {

        Complaint complaint = complaintRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );

        if(!complaint.getComplaintBy().getId().equals(user.getId())){
            throw new RuntimeException("Access Denied");
        }

        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(complaint);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(complaint));
        return ComplaintMapper.toComplaintDetail(complaint,historyList,attachment);

    }

    @Override
    public ComplaintDetail getComplaintDetailByComplaintCode(String complaintCode) {

        Complaint complaint = complaintRepository.findByComplaintCode(complaintCode).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(complaint);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(complaint));
        return ComplaintMapper.toComplaintDetail(complaint,historyList, attachment);
    }

    //TODO: complete updateComplaintDetails Method
    @Override
    public ComplaintDetail updateComplaintDetails(Long id, UserSummary user, ComplaintUpdateRequest request){
        return null;
    }

    @Override
    public Page<ComplaintSummary> getComplaintsForOfficer(User officer, List<String> categories,
                                                          List<String> statuses, Pageable pageable) {

        //converting strings to enums and null checks
        List<ComplaintCategory> cc = (categories == null || categories.isEmpty())
        ? null : categories.stream().map(ComplaintCategory::valueOf).toList();

        List<ComplaintStatus> cs = (statuses == null || statuses.isEmpty())
        ? null : statuses.stream().map(ComplaintStatus::valueOf).toList();

        Page<Complaint> page = complaintRepository.findByOfficerAndFilters(officer, cc,cs, pageable);

        return page.map(ComplaintMapper::toComplaintSummary);
    }

    @Override
    public ComplaintDetail getComplaintDetailForOfficer(@Nonnull Long id, UserSummary officer) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        if(!complaint.getAssignedOfficer().getId().equals(officer.getId())){
            throw new RuntimeException("Access Denied");
        }
        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(complaint);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(complaint));
        return ComplaintMapper.toComplaintDetail(complaint,historyList, attachment);
    }

    @Override
    public ComplaintDetail updateComplaintStatus(Long id, UserSummary officer, ComplaintUpdateRequest request) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        Region region = regionService.getByRegionCode(officer.getRegionCode());

        complaint.setStatus(ComplaintStatus.valueOf(request.getStatus()));
        complaint.setRemark(request.getRemark());
        complaint.setUpdatedAt(LocalDateTime.now());
        complaint.setActionBy(UserMapper.toUser(officer, region));

        Complaint saved = complaintRepository.save(complaint);
        // saves history automatically by jpa listener

        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(saved);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(saved));
        return ComplaintMapper.toComplaintDetail(saved,historyList, attachment);
    }

    @Override
    public ComplaintDetail assignComplaintToOfficer(Long id) {

        Complaint complaint = complaintRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Complaint Not found")
        );

        List<User> officers = userService.getUsersByRegionAndRole(complaint.getRegion(), Role.ROLE_OFFICER);

        if(officers.isEmpty()){
            throw new RuntimeException("No officers available in this region to assign");
        }
        Random random = new Random();
        int n = random.nextInt(officers.size());
        complaint.setAssignedOfficer(officers.get(n));
        Complaint saved = complaintRepository.save(complaint);

        // saves history automatically by jpa listener

        List<ComplaintHistory> historyList = historyService.getComplaintOrderByUpdatedAtAsc(saved);
        AttachmentDto attachment = AttachmentMapper.toAttachmentDto(attachmentService.getByComplaint(saved));
        return ComplaintMapper.toComplaintDetail(saved,historyList, attachment);
    }

    @Override
    public Page<ComplaintSummary> getComplaintForAdmin(Long regionId, Long officerId, List<String> status, String fromDate, String toDate, Pageable pageable) {
        return null;
    }




    private String generateComplaintCode(Role role){

        final String refNumbers = "0123456789";
        final Random random = new SecureRandom();
        StringBuilder builderString = new StringBuilder();

        for(int i=0;i<8;i++){ //refNumbers length == 8
            builderString.append(refNumbers.charAt(random.nextInt(refNumbers.length())));
        }
        return switch(role) {
            case Role.ROLE_ADMIN -> "CMPA";
            case Role.ROLE_OFFICER -> "CMPO";
            case Role.ROLE_P_USER -> "CMPP";
            case Role.ROLE_USER -> "CMPC";
        } + builderString;
    }
}

package com.mk.ipapp.service.Impl;

import com.mk.ipapp.dto.UserMapper;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.complaint.*;
import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.ComplaintCategory;
import com.mk.ipapp.enums.ComplaintStatus;
import com.mk.ipapp.enums.Role;
import com.mk.ipapp.repository.*;
import com.mk.ipapp.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ComplaintServiceImpl implements ComplaintService {


    private final AttachmentService attachmentService;
    private final ComplaintRepository complaintRepository;
    private final UserService userService;
    private final RegionService regionService;
    private final ComplaintHistoryService historyService;
    private final ComplaintMapper complaintMapper;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository,
                                RegionService regionService,
                                UserService userService,
                                AttachmentService attachmentService,
                                ComplaintHistoryService historyService,
    ComplaintMapper complaintMapper) {
        this.complaintRepository = complaintRepository;
        this.regionService = regionService;
        this.userService = userService;
        this.attachmentService = attachmentService;
        this.historyService = historyService;
        this.complaintMapper = complaintMapper;
    }

    @Override
    public ComplaintDetail CreateComplaint(ComplaintCreateRequest request, UserSummary user) {

        Region region = regionService.getByRegionCode(user.getRegionCode());

        Role role = Role.valueOf(user.getRole());

        Complaint complaint = Complaint.builder()
                .complaintCode(generateRefNumber(role))
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(ComplaintStatus.PENDING)
                .complaintBy(UserMapper.toUser(user,region))
                .region(region)
                .createdAt(request.getCreatedAt())
                .updateAt(request.getUpdatedAt())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Complaint saved = complaintRepository.save(complaint);


        return complaintMapper.toComplaintDetail(saved);
    }

    @Override
    public Page<ComplaintSummary> getComplaintsForUser(User user, String status, Pageable pageable) {

        Page<Complaint> page;

        if(status != null){
            ComplaintStatus s = ComplaintStatus.valueOf(status);
            page = complaintRepository.findByUserAndStatus(user, s, pageable);
        }else{
            page = complaintRepository.findByUser(user, pageable);
        }

        return page.map(ComplaintMapper::toComplaintSummary);

    }

    @Override
    public ComplaintDetail getComplaintDetailForUser(Long complaintId, UserSummary user) {

        Complaint complaint = complaintRepository.findByComplaintId(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );

        if(!complaint.getComplaintBy().getId().equals(user.getId())){
            throw new RuntimeException("Access Denied");
        }

        return complaintMapper.toComplaintDetail(complaint);

    }

    @Override
    public ComplaintDetail getComplaintDetailByComplaintCode(String complaintCode) {

        Complaint complaint = complaintRepository.findByComplaintCode(complaintCode).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        return complaintMapper.toComplaintDetail(complaint);
    }

    @Override
    public Page<ComplaintSummary> getComplaintsForOfficer(User officer, List<String> categories,
                                                          List<String> statuses, Pageable pageable) {

        List<ComplaintCategory> cc = categories.stream().map(ComplaintCategory::valueOf).toList();
        List<ComplaintStatus> cs = statuses.stream().map(ComplaintStatus::valueOf).toList();

        Page<Complaint> page = complaintRepository.findByUserAndFilters(officer, cc,cs, pageable);

        return page.map(ComplaintMapper::toComplaintSummary);
    }

    @Override
    public ComplaintDetail getComplaintDetailForOfficer(Long complaintId, UserSummary officer) {
        Complaint complaint = complaintRepository.findByComplaintId(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        if(!complaint.getAssignedOfficer().getId().equals(officer.getId())){
            throw new RuntimeException("Access Denied");
        }
        return complaintMapper.toComplaintDetail(complaint);
    }

    @Override
    public ComplaintDetail updateComplaintStatus(Long complaintId, UserSummary officer, ComplaintUpdateRequest request) {
        Complaint complaint = complaintRepository.findByComplaintId(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found")
        );
        Region region = regionService.getByRegionCode(officer.getRegionCode());

        complaint.setStatus(ComplaintStatus.valueOf(request.getStatus()));
        complaint.setRemark(request.getRemark());
        complaint.setUpdateAt(LocalDateTime.now());
        complaint.setUpdatedBy(UserMapper.toUser(officer, region));

        Complaint saved = complaintRepository.save(complaint);

        return complaintMapper.toComplaintDetail(saved);
    }

    @Override
    public ComplaintDetail assignComplaintToOfficer(Long complaintId) {

        Complaint complaint = complaintRepository.findByComplaintId(complaintId).orElseThrow(
                ()-> new RuntimeException("Complaint Not found")
        );

        List<User> officers = userService.getUsersByRegionAndRole(complaint.getRegion(), Role.ROLE_OFFICER);

        Random random = new Random();
        int n = random.nextInt(officers.size());
        complaint.setAssignedOfficer(officers.get(n));
        Complaint saved = complaintRepository.save(complaint);

        return complaintMapper.toComplaintDetail(saved);
    }

    @Override
    public Page<ComplaintSummary> getComplaintForAdmin(Long regionId, Long officerId, List<String> status, String fromDate, String toDate, Pageable pageable) {
        return null;
    }




    private String generateRefNumber(Role role){

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
        } + builderString.toString();
    }
}

package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.enums.ComplaintCategory;
import com.mk.ipapp.enums.ComplaintStatus;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByComplaintId(Long complaintId);

    Optional<Complaint> findByComplaintCode(String complaintCode);

    List<Complaint> findByUser(User citizen);

    Page<Complaint> findByUserAndFilters(User user, List<ComplaintCategory> categories,
                                         List<ComplaintStatus> statuses,Pageable pageable);

    Page<Complaint> findByUserAndStatus(User user, ComplaintStatus status, Pageable pageable);

    Page<Complaint> findByUser(User user, Pageable pageable);

    List<Complaint> findByRegion(Region region);

    List<Complaint> findByRegionAndStatus(Region region, ComplaintStatus status);

    List<Complaint> findByAssignedOfficer(User officer);

}

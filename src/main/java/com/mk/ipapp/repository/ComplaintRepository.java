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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {


    Optional<Complaint> findByComplaintCode(String complaintCode);

    List<Complaint> findByUser(User complaintBy);

    Page<Complaint> findByUserAndFilters(User complaintBy, List<ComplaintCategory> categories,
                                         List<ComplaintStatus> statuses,Pageable pageable);

    Page<Complaint> findByUserAndStatus(User complaintBy, ComplaintStatus status, Pageable pageable);

    Page<Complaint> findByUser(User complaintBy, Pageable pageable);

    List<Complaint> findByRegion(Region region);

    List<Complaint> findByRegionAndStatus(Region region, ComplaintStatus status);

    List<Complaint> findByAssignedOfficer(User assignedOfficer);

}

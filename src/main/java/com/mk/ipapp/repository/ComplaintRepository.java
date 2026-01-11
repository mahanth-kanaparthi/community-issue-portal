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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {


    Optional<Complaint> findByComplaintCode(String complaintCode);

    List<Complaint> findByComplaintBy(User complaintBy);

    //custom queries
    @Query("SELECT c FROM Complaint c WHERE c.complaintBy = :complaintBy "+
    "AND (:categories IS NULL OR c.category IN :categories) "+
    "AND (:statuses IS NULL OR c.status IN :statuses)")
    Page<Complaint> findByComplaintByAndFilters(
            @Param("complaintBy") User complaintBy,
            @Param("categories") List<ComplaintCategory> categories,
            @Param("statuses") List<ComplaintStatus> statuses,
            Pageable pageable);

    @Query("SELECT c FROM Complaint c WHERE c.assignedOfficer = :officer "+
    "AND (:categories IS NULL OR c.category IN :categories) "+
    "AND (:statuses IS NULL OR c.status IN :statuses)")
    Page<Complaint> findByOfficerAndFilters(
            @Param("officer") User officer,
            @Param("categories") List<ComplaintCategory> categories,
            @Param("statuses") List<ComplaintStatus> statuses,
            Pageable pageable
    );

    Page<Complaint> findByComplaintByAndStatus(User complaintBy, ComplaintStatus status, Pageable pageable);

    Page<Complaint> findByComplaintBy(User complaintBy, Pageable pageable);

    List<Complaint> findByRegion(Region region);

    List<Complaint> findByRegionAndStatus(Region region, ComplaintStatus status);

    List<Complaint> findByAssignedOfficer(User assignedOfficer);

}

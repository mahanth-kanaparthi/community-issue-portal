package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintHistoryRepository extends JpaRepository<ComplaintHistory, Long> {

    List<ComplaintHistory> findByComplaintOrderByUpdatedAtAsc(Complaint complaint);

}

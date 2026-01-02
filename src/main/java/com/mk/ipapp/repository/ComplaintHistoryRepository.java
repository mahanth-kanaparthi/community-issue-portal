package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplaintHistoryRepository extends JpaRepository<ComplaintHistoryRepository, Long> {

    List<ComplaintHistory> findByComplaintOrderByUpdatedAtAsc(Complaint complaint);

}

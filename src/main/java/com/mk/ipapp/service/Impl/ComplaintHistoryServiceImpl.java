package com.mk.ipapp.service.Impl;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.repository.ComplaintHistoryRepository;
import com.mk.ipapp.service.ComplaintHistoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintHistoryServiceImpl implements ComplaintHistoryService {

    private final ComplaintHistoryRepository complaintHistoryRepository;


    @Override
    @Transactional
    public void recordHistory(Complaint complaint, String remark, User user) {
        ComplaintHistory history = ComplaintHistory.builder()
                .complaint(complaint)
                .status(complaint.getStatus())
                .remark(remark)
                .actionBy(user)
                .updatedAt(LocalDateTime.now())
                .build();
        complaintHistoryRepository.save(history);
    }

    @Override
    public List<ComplaintHistory> getComplaintOrderByUpdatedAtAsc(Complaint complaint) {
        return complaintHistoryRepository.findByComplaintOrderByUpdatedAtAsc(complaint);

    }
}

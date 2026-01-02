package com.mk.ipapp.service.Impl;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.repository.ComplaintHistoryRepository;
import com.mk.ipapp.service.ComplaintHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintHistoryServiceImpl implements ComplaintHistoryService {

    private final ComplaintHistoryRepository complaintHistoryRepository;

    ComplaintHistoryServiceImpl(ComplaintHistoryRepository complaintHistoryRepository){
        this.complaintHistoryRepository = complaintHistoryRepository;
    }

    @Override
    public List<ComplaintHistory> getComplaintOrderByUpdatedAtAsc(Complaint complaint) {
        return complaintHistoryRepository.findByComplaintOrderByUpdatedAtAsc(complaint);

    }
}

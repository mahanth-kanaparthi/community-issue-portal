package com.mk.ipapp.entity.listener;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.repository.ComplaintHistoryRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;

public class ComplaintListener {

    private static ComplaintHistoryRepository repository;

    @Autowired
    public void setRepository(ComplaintHistoryRepository repository){
        ComplaintListener.repository = repository;
    }

    @PostPersist
    public void afterCreate(Complaint complaint){
        saveHistory(complaint, "created by: "+complaint.getComplaintBy().getFullName());
    }

    @PostUpdate
    public void afterUpdate(Complaint complaint){
        saveHistory(complaint, "status updated to: "+complaint.getStatus().name() +" by "+complaint.getActionBy().getFullName());
    }

    private void saveHistory(Complaint complaint, String remark){
        ComplaintHistory history = ComplaintHistory.builder()
                .complaint(complaint)
                .status(complaint.getStatus())
                .remark(remark)
                .actionBy(complaint.getActionBy())
                .updatedAt(complaint.getUpdatedAt())
                .build();
        repository.save(history);
    }
}

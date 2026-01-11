package com.mk.ipapp.service;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;
import com.mk.ipapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface ComplaintHistoryService {

    void recordHistory(Complaint complaint, String remark, User user);

    List<ComplaintHistory> getComplaintOrderByUpdatedAtAsc(Complaint complaint);
}

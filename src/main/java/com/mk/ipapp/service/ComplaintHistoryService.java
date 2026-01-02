package com.mk.ipapp.service;

import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.entity.ComplaintHistory;

import java.util.List;
import java.util.Optional;

public interface ComplaintHistoryService {

    List<ComplaintHistory> getComplaintOrderByUpdatedAtAsc(Complaint complaint);
}

package com.mk.ipapp.repository;

import com.mk.ipapp.entity.Attachment;
import com.mk.ipapp.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findByFileName(String filename);
    Attachment findByComplaint(Complaint complaint);
}

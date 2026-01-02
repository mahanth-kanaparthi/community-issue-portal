package com.mk.ipapp.service;

import com.mk.ipapp.entity.Attachment;
import com.mk.ipapp.entity.Complaint;

public interface AttachmentService {

    Attachment getByComplaint(Complaint complaint);

    Attachment getByFileName(String fileName);

    Attachment saveAttachment(Attachment attachment);
}

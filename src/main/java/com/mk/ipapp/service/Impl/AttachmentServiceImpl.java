package com.mk.ipapp.service.Impl;

import com.mk.ipapp.entity.Attachment;
import com.mk.ipapp.entity.Complaint;
import com.mk.ipapp.repository.AttachmentRepository;
import com.mk.ipapp.service.AttachmentService;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
    @Override
    public Attachment getByComplaint(Complaint complaint) {

        return attachmentRepository.findByComplaint(complaint);
    }

    @Override
    public Attachment getByFileName(String fileName) {
        return attachmentRepository.findByFileName(fileName);
    }

    @Override
    public Attachment saveAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
}

package com.mk.ipapp.dto.complaint.attachment;

import com.mk.ipapp.entity.Attachment;

public class AttachmentMapper {

    public static AttachmentDto toAttachmentDto(Attachment attachment){
        return AttachmentDto.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .imageData(attachment.getImageData())
                .uploadedAt(attachment.getUploadedAt().toString())
                .build();
    }

}

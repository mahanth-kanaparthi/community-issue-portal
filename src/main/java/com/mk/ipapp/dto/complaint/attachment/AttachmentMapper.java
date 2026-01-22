package com.mk.ipapp.dto.complaint.attachment;

import com.mk.ipapp.entity.Attachment;
import java.time.LocalDateTime;

public class AttachmentMapper {

    public static AttachmentDto toAttachmentDto(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        return AttachmentDto.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .imageData(attachment.getImageData())
                .uploadedAt(
                        attachment.getUploadedAt() != null
                                ? attachment.getUploadedAt().toString()
                                : null
                )
                .build();
    }

    public static Attachment toAttachment(AttachmentDto attachmentDto) {
        if (attachmentDto == null) {
            return null;
        }

        Attachment attachment = Attachment.builder()
                .id(attachmentDto.getId())
                .fileName(attachmentDto.getFileName())
                .imageData(attachmentDto.getImageData())
                .build();

        // uploadedAt is usually auto-generated (@CreationTimestamp)
        if (attachmentDto.getUploadedAt() != null) {
            attachment.setUploadedAt(LocalDateTime.parse(attachmentDto.getUploadedAt()));
        }

        // complaint will be set in service layer
        return attachment;
    }
}


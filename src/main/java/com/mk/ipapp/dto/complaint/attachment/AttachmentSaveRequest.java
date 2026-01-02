package com.mk.ipapp.dto.complaint.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttachmentSaveRequest {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private byte[] imageData;
    private LocalDateTime uploadedAt = LocalDateTime.now();
}

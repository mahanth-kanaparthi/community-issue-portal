package com.mk.ipapp.dto.complaint.attachment;

import jdk.jfr.SettingDefinition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AttachmentDto {

    private Long id;
    private String fileName;
    private byte[] imageData;
    private String uploadedAt;
}

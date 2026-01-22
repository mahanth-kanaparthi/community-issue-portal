package com.mk.ipapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = true)
    private String fileUrl; // local path or cloud url

    @Column(nullable = true)
    private String filetype; // image or png etc.

    @Lob
    @Column(name = "imagedata", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @CreationTimestamp
    private LocalDateTime uploadedAt;


}

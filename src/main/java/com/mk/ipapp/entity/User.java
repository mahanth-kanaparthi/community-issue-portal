package com.mk.ipapp.entity;

import com.mk.ipapp.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region")
    private Region region;

    @Column(nullable = false)
    private Boolean active;


    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "complaintBy")
    private List<Complaint> complaintsRaised;

    @OneToMany(mappedBy = "assignedOfficer")
    private List<Complaint> complaintsAssigned;
}

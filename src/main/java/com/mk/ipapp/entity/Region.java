package com.mk.ipapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "regions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "region_code", unique = true, nullable = false)
    private Long regionCode;

    @OneToMany(mappedBy = "region")
    private List<User> users; //optional back reference

    @CreationTimestamp
    private LocalDateTime createdAt;
}

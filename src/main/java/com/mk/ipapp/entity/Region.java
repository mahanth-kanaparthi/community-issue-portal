package com.mk.ipapp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "regions")
@Builder
@Getter
@Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long regionCode;

    @OneToMany(mappedBy = "region")
    private List<User> users; //optional back reference

    private LocalDateTime createdAt = LocalDateTime.now();
}

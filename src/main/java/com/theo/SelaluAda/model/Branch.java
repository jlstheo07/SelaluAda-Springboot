package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_branch;

    @Column(nullable = false, unique = true)
    private String name_branch;

    @Column(nullable = false)
    private String alamat_branch;

    @Column(nullable = false)
    private Double latitude_branch;

    @Column(nullable = false)
    private Double longitude_branch;
}

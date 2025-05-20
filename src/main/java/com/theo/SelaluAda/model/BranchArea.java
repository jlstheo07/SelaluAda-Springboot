package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Branch")
public class BranchArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branch_id;

    @Column(nullable = false, unique = true)
    private String namaCabang;

    @Column(nullable = false)
    private String provinsi;
}
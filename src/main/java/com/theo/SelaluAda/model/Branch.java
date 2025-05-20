package com.theo.SelaluAda.model;

import com.theo.SelaluAda.enums.ProvinceToBranch;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID branch_id;

    @Column(name = "namaCabang", nullable = false)
    private String namaCabang;

    @Column(nullable = false)
    private String alamat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProvinceToBranch area; // 👈 Tambahkan ini

}

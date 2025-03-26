package com.theo.SelaluAda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Plafond")
public class Plafond {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_plafon;

    @Column(nullable = false)
    private String jenis_plafon;

    @Column(nullable = false)
    private Double jumlah_plafon;
}
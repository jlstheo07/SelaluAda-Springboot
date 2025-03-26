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
@Table(name = "Pengajuan")
public class Pengajuan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_pengajuan;

    @ManyToOne
    @JoinColumn (name = "id_customer")
    private UserCustomer id_customer;

    @Column(nullable = false)
    private String status;

    @Column(nullable=false)
    private Double amount;

    @Column(nullable=false)
    private Integer tenor;

    @Column(nullable=false)
    private Double angsuran;
}
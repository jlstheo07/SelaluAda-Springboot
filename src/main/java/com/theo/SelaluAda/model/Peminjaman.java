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
@Table (name = "Peminjaman")

public class Peminjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private UserCustomer id_customer;

    @Column(nullable = false)
    private Double jumlah_pinjaman;

    @Column(nullable = false)
    private Integer tenor;

    @Column(nullable = false)
    private Double angsuran;

    @Column(nullable = false)
    private Double bunga;

    @Column(nullable = false)
    private Integer sisa_tenor;

    @Column(nullable = false)
    private Double sisa_pokok_hutang;

}

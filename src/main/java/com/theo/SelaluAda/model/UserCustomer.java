package com.theo.SelaluAda.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "UserCustomer")
public class UserCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_customer;

    @OneToOne
    @JoinColumn(name = "id_user", unique = true) // Relasi ke Users
    private User users;

    @Column(nullable = false)
    private String tempat_tgl_lahir;

    @Column(nullable = false)
    private String no_telp;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private String nik;

    @Column(nullable = false)
    private String nama_ibu_kandung;

    @Column(nullable = false)
    private String pekerjaan;

    @Column(nullable = false)
    private String gaji;

    @Column(nullable = false)
    private String no_rek;

    @Column(nullable = false)
    private String status_rumah;

    @ManyToOne
    @JoinColumn(name = "id_plafon")
    private Plafond plafond;

    private Double sisa_plafon;
}
package com.theo.SelaluAda.model;


import com.theo.SelaluAda.enums.PlafondLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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

    @Column(nullable = false, unique = true)
    private String nik;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private String provinsi;

    @Column(nullable = false)
    private String tempat_lahir;

    @Column(nullable = false)
    private Date tanggal_lahir;

    @Column
    private String gender;

    @Column(nullable = false)
    private String pekerjaan;

    @Column(nullable = false)
    private Long gaji;

    @Column
    private String bank;

    @Column
    private Long rekening;

    @Column(nullable = false)
    private Double plafond;

    @Column(nullable = false)
    private Double sisa_plafond;

    @Column(nullable = false)
    private String no_hp;

    @Column(nullable = false)
    private String nama_ibu_kandung;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @Column(name = "foto_ktp_url")
    private String fotoKtpUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "plafond_level")
    private PlafondLevel plafondLevel;

}
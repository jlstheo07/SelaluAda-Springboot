package com.theo.SelaluAda.model;


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

    @Column(nullable = false)
    private String pekerjaan;

    @Column(nullable = false)
    private Long gaji;

    @Column(nullable = false)
    private Double plafond;

    @Column(nullable = false)
    private Double sisa_plafond;

    @Column(nullable = false)
    private String no_hp;

    @Column(nullable = false)
    private String nama_ibu_kandung;

    @ManyToOne
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

}
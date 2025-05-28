package com.theo.SelaluAda.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerResponseDTO {
    private String username;
    private String email;
    private String nama_lengkap;

    private String nik;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private String gender;
    private String pekerjaan;
    private Long gaji;
    private String bank;
    private Long rekening;
    private Double plafond;
    private Double sisa_plafond;
    private String no_hp;
    private String nama_ibu_kandung;
    private String alamat;
    private String provinsi;
    private String namaCabang;
    private String areaCabang;
}
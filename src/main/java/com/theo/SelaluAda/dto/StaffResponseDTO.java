package com.theo.SelaluAda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseDTO {
    private UUID staffId;
    private Long nip;
    private String namaCabang;
    private String username;
    private String email;
    private String nama_lengkap;
    private String role;
}

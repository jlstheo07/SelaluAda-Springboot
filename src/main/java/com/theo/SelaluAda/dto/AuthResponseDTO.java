package com.theo.SelaluAda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String role_id;
    private String username;
    private String role;
    private String customerId; // ✅ Tambahkan ini
}
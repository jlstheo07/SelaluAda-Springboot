package com.theo.SelaluAda.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterStaffReqDTO {
    private String username;
    private String password;
    private String email;
    private String namaLengkap;
    private Long nip;
    private UUID branchId; // id cabang untuk branch manager sama marketing
    private UUID roleId;   // id role (misal marketing, branch manager, backoffice)

}

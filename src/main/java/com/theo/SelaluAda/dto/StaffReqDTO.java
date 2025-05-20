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
public class StaffReqDTO {
    private Long nip;
    private UUID branchId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
}
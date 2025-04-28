package com.theo.SelaluAda.dto;

import lombok.Data;

@Data
public class StaffRequest {

    private String email_staff; //email
    private String password_staff;
    private String name_staff;
    private String nip_staff;
}
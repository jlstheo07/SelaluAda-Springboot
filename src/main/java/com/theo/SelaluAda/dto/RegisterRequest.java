package com.theo.SelaluAda.dto;

import lombok.Data;

//import java.util.UUID;
@Data
public class RegisterRequest {

    private String username; //email
    private String password;
    private String name;
}
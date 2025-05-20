package com.theo.SelaluAda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthReqDTO {
    private String usernameOrEmail;
    private String password;
    private String fcmToken;
}

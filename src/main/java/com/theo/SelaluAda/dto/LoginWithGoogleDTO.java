package com.theo.SelaluAda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginWithGoogleDTO {
    private String idToken;
    private String fcmToken;
}
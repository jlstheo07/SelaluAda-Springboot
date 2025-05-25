package com.theo.SelaluAda.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequestDTO {

    @NotBlank(message = "Token harus diisi")
    private String token;

    @NotBlank(message = "Password baru harus diisi")
    private String newPassword;

    // Getter & Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

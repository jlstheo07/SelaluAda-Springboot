package com.theo.SelaluAda.dto;

public class StaffLogin {
    private String nip_staff;
    private String password_staff;

    // Constructor
    public StaffLogin() {}

    public StaffLogin(String nip_staff, String password_staff) {
        this.nip_staff = nip_staff;
        this.password_staff = password_staff;
    }

    // Getter & Setter
    public String getNip_staff() {
        return nip_staff;
    }

    public void setNip_staff(String nip_staff) {
        this.nip_staff = nip_staff;
    }

    public String getPassword_staff() {
        return password_staff;
    }

    public void setPassword_staff(String password_staff) {
        this.password_staff = password_staff;
    }
}

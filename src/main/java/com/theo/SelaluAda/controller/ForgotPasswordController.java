package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.dto.ForgotPasswordRequestDTO;
import com.theo.SelaluAda.services.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        forgotPasswordService.processForgotPassword(request.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Silahkan cek email anda, link ganti password sudah dikirimkan.");
        return ResponseEntity.ok(response);
    }

}

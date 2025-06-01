package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.dto.*;
import com.theo.SelaluAda.services.AuthService;
import com.theo.SelaluAda.util.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthReqDTO request) {
        try {
            AuthResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDTO> logout(@RequestHeader("Authorization") String authHeader,
                                                     @RequestBody(required = false) LogoutRequestDTO request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Token tidak valid atau tidak ada."));
        }
        String token = authHeader.substring(7);
        authService.logout(token, request != null ? request.getFcmToken() : null);
        return ResponseEntity.ok(new MessageResponseDTO("Logout berhasil."));
    }


    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequestDTO request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = jwtUtil.extractidUser(token);

        authService.changePassword(username, request);
        return ResponseEntity.ok("Password berhasil diubah.");
    }
}

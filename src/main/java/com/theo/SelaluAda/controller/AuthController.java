package com.theo.SelaluAda.controller;

import com.theo.SelaluAda.dto.*;
import com.theo.SelaluAda.services.AuthService;
import com.theo.SelaluAda.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/loginStaff")
    public ResponseEntity<?> loginStaff(@RequestBody StaffLogin loginRequest) {
        String token = authService.authenticateUser(loginRequest.getNip_staff(), loginRequest.getPassword_staff());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token tidak valid atau tidak ada.");
        }

        String token = authHeader.substring(7); // Menghapus "Bearer " dari token
        authService.logout(token);

        return ResponseEntity.ok("Logout berhasil.");
    }




    @GetMapping("/getidUser")
    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String token) {
        System.out.println("Received Token: [" + token + "]");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        token = token.trim(); // Hapus spasi tambahan

        System.out.println("Processed Token: [" + token + "]");

        try {
            String id_user = jwtUtil.extractidUser(token);
            Map<String, String> response = new HashMap<>();
            response.put("id_user", id_user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token: " + e.getMessage());
        }
    }

    @PostMapping("/registerAkunCustomer")
    public ResponseEntity<?> registkun(@RequestBody RegisterRequest RegisterRequest) {
        authService.registerCustomer(RegisterRequest);

        String token = authService.authenticateUser(RegisterRequest.getUsername(), RegisterRequest.getPassword());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping ("/registerAkunStaff")
    public ResponseEntity<?> staffregis(@RequestBody StaffRequest StaffRequest){
        authService.registerStaff(StaffRequest);

        String token = authService.authenticateUser(StaffRequest.getEmail_staff(), StaffRequest.getPassword_staff());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }



}
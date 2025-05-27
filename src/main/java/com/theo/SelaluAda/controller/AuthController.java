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

//    private JwtUtil jwtUtil;
//    @Autowired
//    private AuthService authService;
//
//
//
//    @PostMapping("/login")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
//
//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    @PostMapping("/loginStaff")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<?> loginStaff(@RequestBody StaffLogin loginRequest) {
//        String token = authService.authenticateUser(loginRequest.getNip_staff(), loginRequest.getPassword_staff());
//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    @PostMapping("/logout")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.badRequest().body("Token tidak valid atau tidak ada.");
//        }
//
//        String token = authHeader.substring(7); // Menghapus "Bearer " dari token
//        authService.logout(token);
//
//        return ResponseEntity.ok("Logout berhasil.");
//    }
//
//
//
//
//    @GetMapping("/getidUser")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String token) {
//        System.out.println("Received Token: [" + token + "]");
//
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//
//        token = token.trim(); // Hapus spasi tambahan
//
//        System.out.println("Processed Token: [" + token + "]");
//
//        try {
//            String id_user = jwtUtil.extractidUser(token);
//            Map<String, String> response = new HashMap<>();
//            response.put("id_user", id_user);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/registerAkunCustomer")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<?> registkun(@RequestBody RegisterRequest RegisterRequest) {
//        authService.registerCustomer(RegisterRequest);
//
//        String token = authService.authenticateUser(RegisterRequest.getUsername(), RegisterRequest.getPassword());
//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    @PostMapping ("/registerAkunStaff")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<?> staffregis(@RequestBody StaffRequest StaffRequest){
//        authService.registerStaff(StaffRequest);
//
//        String token = authService.authenticateUser(StaffRequest.getEmail_staff(), StaffRequest.getPassword_staff());
//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

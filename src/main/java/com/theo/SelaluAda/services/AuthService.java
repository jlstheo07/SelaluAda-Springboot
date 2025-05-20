package com.theo.SelaluAda.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.theo.SelaluAda.dto.AuthReqDTO;
import com.theo.SelaluAda.dto.AuthResponseDTO;
import com.theo.SelaluAda.dto.ChangePasswordRequestDTO;
import com.theo.SelaluAda.dto.LoginWithGoogleDTO;
import com.theo.SelaluAda.model.BlacklistedToken;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.BlacklistedTokenRepository;
import com.theo.SelaluAda.repository.RoleRepository;
import com.theo.SelaluAda.repository.UserRepository;
import com.theo.SelaluAda.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final FCMTokenService fcmTokenService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, FCMTokenService fcmTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.fcmTokenService = fcmTokenService;
    }

    public AuthResponseDTO login(AuthReqDTO request) {
        String identifier = request.getUsernameOrEmail();
        String password = request.getPassword();

        System.out.println("Login request: " + request.getUsernameOrEmail() + ", FCM: " + request.getFcmToken());

        User user = identifier.contains("@")
                ? userRepository.findByEmail(identifier).orElseThrow(() -> new UsernameNotFoundException("User dengan email tidak ditemukan"))
                : userRepository.findByUsername(identifier).orElseThrow(() -> new UsernameNotFoundException("User dengan username tidak ditemukan"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password salah.");
        }
        if (request.getFcmToken() != null && user.getRole().getNamaRole().equalsIgnoreCase("CUSTOMER")) {
            fcmTokenService.saveToken(user, request.getFcmToken());
        }


        String token = jwtUtil.generateToken(user.getUsername());
        String role = user.getRole().getNamaRole();
        String role_id = user.getRole().getRoleId().toString();

        // ✅ Ambil customer ID jika ada
        String customerId = null;
        if (user.getCustomer() != null) {
            customerId = user.getCustomer().getId_customer().toString();
        }

        return new AuthResponseDTO(token, role_id, user.getUsername(), role, customerId);
    }

    @Transactional
    public void logout(String token, String fcmToken) {
        // Simpan token ke blacklist dengan expiry date
        Date expiryDate = jwtUtil.extractExpiration(token);
        LocalDateTime localExpiryDate = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        BlacklistedToken blacklistedToken = new BlacklistedToken(token, localExpiryDate);
        blacklistedTokenRepository.save(blacklistedToken);

        // Hapus FCM token jika ada
        String username = jwtUtil.extractidUser(token);
        userRepository.findByUsername(username).ifPresent(user -> {
            if (fcmToken != null) {
                fcmTokenService.deleteToken(fcmToken);
            }
        });
    }

    public void changePassword(String username, ChangePasswordRequestDTO request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Password lama tidak sesuai.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
            .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList("29525165356-5ppm1frk6aldubkfum83ks1ok9knkoe2.apps.googleusercontent.com")) // ganti dengan client ID dari Firebase
            .build();

    public AuthResponseDTO loginWithGoogle(LoginWithGoogleDTO request) {
        try {
            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken == null) {
                throw new RuntimeException("Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElseGet(() -> {
                Role defaultRole = roleRepository.findByNamaRole("CUSTOMER")
                        .orElseThrow(() -> new RuntimeException("Role Customer tidak ditemukan"));

                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(email);
                newUser.setNama_lengkap(name);
                newUser.setPassword("");
                newUser.setRole(defaultRole);

                return userRepository.save(newUser);
            });

            if (request.getFcmToken() != null && user.getRole().getNamaRole().equalsIgnoreCase("CUSTOMER")) {
                fcmTokenService.saveToken(user, request.getFcmToken());
            }

            String token = jwtUtil.generateToken(user.getUsername());
            String role = user.getRole().getNamaRole();
            String roleId = user.getRole().getRoleId().toString();
            String customerId = user.getCustomer() != null
                    ? user.getCustomer().getId_customer().toString()
                    : null;

            return new AuthResponseDTO(token, roleId, user.getUsername(), role, customerId);
        } catch (Exception e) {
            throw new RuntimeException("Google login failed: " + e.getMessage());
        }
    }
}
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthService.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private BlacklistedTokenRepository blacklistedTokenRepository;
//
//
//
//    public String authenticateUser(String email, String password) {
//        logger.info("Mencoba login dengan email: {}");  // Cek apakah email diterima
//
//
//        Optional<User> userOptional = userRepository.findByEmail((email));
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            logger.info("User ditemukan: {}"); // Konfirmasi user ditemukan di database
//
//            String storedPassword = user.getPassword();
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//            boolean isPasswordMatch;
//
//            // Cek apakah password sudah di-hash (BCrypt selalu diawali dengan "$2a$", "$2b$", atau "$2y$")
//            if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
//                isPasswordMatch = passwordEncoder.matches(password, storedPassword);
//            } else {
//                // Password masih dalam bentuk plain text
//                isPasswordMatch = storedPassword.equals(password);
//            }
//
//            if (isPasswordMatch) {
//                logger.info("Password cocok, menghasilkan token...");
//
//                // Jika password belum di-hash, lakukan hashing dan simpan kembali
////                if (!storedPassword.startsWith("$2a$") && !storedPassword.startsWith("$2b$") && !storedPassword.startsWith("$2y$")) {
////                    String hashedPassword = passwordEncoder.encode(password);
////                    user.setPassword(hashedPassword);
////                    userRepository.save(user); // Simpan perubahan ke database
////                    logger.info("Password telah di-hash dan diperbarui di database.");
////                }
//
//                return jwtUtil.generateToken(user); // Kembalikan JWT Token
//            } else {
//                logger.warn("Password tidak cocok untuk email: {}");
//            }
//        } else {
//            logger.warn("User dengan email {} tidak ditemukan di database");
//        }
//
//        return null; // Jika gagal login
//    }
//
//
//    public User registerCustomer(RegisterRequest request) {
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        User users = new User();
//        users.setEmail(request.getUsername());
//        //users.setPassword(passwordEncoder.encode(request.getPassword())); //hasing password
//        users.setPassword(request.getPassword()); //no-hasing password
//        users.setUsername(request.getName());
//        Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
//        users.setRole(role);
//
//        return userRepository.save(users);
//    }
//
//    public User registerStaff(StaffRequest request) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        User users = new User();
//        users.setEmail(request.getEmail_staff());
//        //users.setPassword(passwordEncoder.encode(request.getPassword())); //hasing password
//        users.setPassword(request.getPassword_staff()); //no-hasing password
//        users.setUsername(request.getPassword_staff());
//        //Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
//        //users.setRole(role);
//        return userRepository.save(users);
//    }
//
//    public void logout(String token) {
//        Date expiryDate = jwtUtil.extractExpiration(token); // ✅ Ambil expiry dari token
//
//        // ✅ Konversi Date → LocalDateTime
//        LocalDateTime localExpiryDate = expiryDate.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime();
//
//        // ✅ Simpan token ke database
//        BlacklistedToken blacklistedToken = new BlacklistedToken(token, localExpiryDate);
//        blacklistedTokenRepository.save(blacklistedToken);
//    }


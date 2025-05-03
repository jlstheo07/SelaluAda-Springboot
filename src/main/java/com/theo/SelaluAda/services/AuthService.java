package com.theo.SelaluAda.services;

import com.theo.SelaluAda.dto.RegisterRequest;
import com.theo.SelaluAda.dto.StaffRequest;
import com.theo.SelaluAda.model.BlacklistedToken;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.BlacklistedTokenRepository;
import com.theo.SelaluAda.repository.UserRepository;
import com.theo.SelaluAda.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;



    public String authenticateUser(String email, String password) {
        logger.info("Mencoba login dengan email: {}");  // Cek apakah email diterima


        Optional<User> userOptional = userRepository.findByEmail((email));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("User ditemukan: {}"); // Konfirmasi user ditemukan di database

            String storedPassword = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            boolean isPasswordMatch;

            // Cek apakah password sudah di-hash (BCrypt selalu diawali dengan "$2a$", "$2b$", atau "$2y$")
            if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
                isPasswordMatch = passwordEncoder.matches(password, storedPassword);
            } else {
                // Password masih dalam bentuk plain text
                isPasswordMatch = storedPassword.equals(password);
            }

            if (isPasswordMatch) {
                logger.info("Password cocok, menghasilkan token...");

                // Jika password belum di-hash, lakukan hashing dan simpan kembali
//                if (!storedPassword.startsWith("$2a$") && !storedPassword.startsWith("$2b$") && !storedPassword.startsWith("$2y$")) {
//                    String hashedPassword = passwordEncoder.encode(password);
//                    user.setPassword(hashedPassword);
//                    userRepository.save(user); // Simpan perubahan ke database
//                    logger.info("Password telah di-hash dan diperbarui di database.");
//                }

                return jwtUtil.generateToken(user); // Kembalikan JWT Token
            } else {
                logger.warn("Password tidak cocok untuk email: {}");
            }
        } else {
            logger.warn("User dengan email {} tidak ditemukan di database");
        }

        return null; // Jika gagal login
    }


    public User registerCustomer(RegisterRequest request) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User users = new User();
        users.setEmail(request.getUsername());
        //users.setPassword(passwordEncoder.encode(request.getPassword())); //hasing password
        users.setPassword(request.getPassword()); //no-hasing password
        users.setUsername(request.getName());
        Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
        users.setRole(role);

        return userRepository.save(users);
    }

    public User registerStaff(StaffRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User users = new User();
        users.setEmail(request.getEmail_staff());
        //users.setPassword(passwordEncoder.encode(request.getPassword())); //hasing password
        users.setPassword(request.getPassword_staff()); //no-hasing password
        users.setUsername(request.getPassword_staff());
        //Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
        //users.setRole(role);
        return userRepository.save(users);
    }

    public void logout(String token) {
        Date expiryDate = jwtUtil.extractExpiration(token); // ✅ Ambil expiry dari token

        // ✅ Konversi Date → LocalDateTime
        LocalDateTime localExpiryDate = expiryDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // ✅ Simpan token ke database
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, localExpiryDate);
        blacklistedTokenRepository.save(blacklistedToken);
    }

}
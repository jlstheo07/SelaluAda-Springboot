package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.PasswordResetToken;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.PasswordResetTokenRepository;
import com.theo.SelaluAda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    @Autowired
    public ForgotPasswordService(UserRepository userRepository,
                                 PasswordResetTokenRepository tokenRepository,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void processForgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Hapus token lama jika ada
            Optional<PasswordResetToken> existingToken = tokenRepository.findByUser(user);
            existingToken.ifPresent(tokenRepository::delete);

            // Generate token baru
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);
            PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);

            tokenRepository.save(resetToken);

            // Kirim email reset password
            String resetLink = "http://localhost:4200/reset-password?token=" + token;
            emailService.sendResetPasswordEmail(email, resetLink);

            System.out.println("Reset link: " + resetLink);
        } else {
            System.out.println("User tidak ditemukan untuk email: " + email);
        }
    }
}
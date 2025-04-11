package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.PasswordResetToken;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.PasswordResetTokenRepository;
import com.theo.SelaluAda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ResetPasswordService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordService(PasswordResetTokenRepository tokenRepository,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);

        if (tokenOptional.isPresent()) {
            PasswordResetToken resetToken = tokenOptional.get();

            if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                // Token expired
                tokenRepository.delete(resetToken);
                return false;
            }

            User user = resetToken.getUser();
            //user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // Hapus token setelah berhasil reset
            tokenRepository.delete(resetToken);

            return true;
        }

        return false; // token tidak ditemukan
    }
}
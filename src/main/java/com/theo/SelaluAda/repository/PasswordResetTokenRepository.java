package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.PasswordResetToken;
import com.theo.SelaluAda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
}
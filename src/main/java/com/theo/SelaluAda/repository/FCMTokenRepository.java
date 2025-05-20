package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.model.FCMToken;
import com.theo.SelaluAda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FCMTokenRepository extends JpaRepository<FCMToken, UUID> {
    Optional<FCMToken> findByToken(String token);
    void deleteByToken(String token);
    List<FCMToken> findByUser(User user);
}
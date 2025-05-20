package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.FCMToken;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.FCMTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FCMTokenService {

    private final FCMTokenRepository fcmTokenRepository;

    public FCMTokenService(FCMTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    public void saveToken(User user, String token) {
        Optional<FCMToken> existing = fcmTokenRepository.findByToken(token);
        if (existing.isEmpty()) {
            FCMToken fcmToken = new FCMToken();
            fcmToken.setUser(user);
            fcmToken.setToken(token);
            fcmTokenRepository.save(fcmToken);
        }
    }

    @Transactional
    public void deleteToken(String token) {
        fcmTokenRepository.deleteByToken(token);
    }
}
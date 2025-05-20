package com.theo.SelaluAda.security;

import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));

        // Ambil nama role dari relasi ManyToOne
        String roleName = user.getRole().getNamaRole(); // ⬅️ Sesuaikan nama field-nya
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Atau user.getUsername(), tergantung yang dipakai untuk login
                user.getPassword(),
                Collections.singleton(authority)
        );
    }
}

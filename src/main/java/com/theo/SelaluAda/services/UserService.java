package com.theo.SelaluAda.services;

import com.theo.SelaluAda.dto.UserResponseDTO;
import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.RoleRepository;
import com.theo.SelaluAda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsersDTO() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponseDTO(
                user.getId_user(),
                user.getUsername(),
                user.getEmail(),
                user.getNama_lengkap(),
                user.getRole().getNamaRole()
        )).toList();
    }

    public Optional<UserResponseDTO> getUserDTOByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserResponseDTO(
                        user.getId_user(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getNama_lengkap(),
                        user.getRole().getNamaRole()
                ));
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // Cek apakah username atau email sudah digunakan sebelum menyimpan
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username sudah digunakan!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        // Encode password sebelum menyimpan
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Cari role "Customer" dari database
        Role defaultRole = roleRepository.findByNamaRole("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role Customer tidak ditemukan"));

        // Set default role jika belum ada
        if (user.getRole() == null) {
            user.setRole(defaultRole);
        }

        return userRepository.save(user);
    }

    public User updateUser(UUID id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        }).orElse(null);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}



//private final UserRepository userRepository;
//private final RoleService roleService;
//
//
//public UserService(UserRepository userRepository, RoleService roleService) {
//    this.userRepository = userRepository;
//    this.roleService = roleService;
//}
//
//public List<User> getAllUsers() {
//    return userRepository.findAll();
//}
//
//public Optional<User> getUserById(UUID id) {
//    return userRepository.findById(id);
//}
//
//public User createUser(User user) {
//    Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
//    user.setRole(role);
//    return userRepository.save(user);
//}
//
//public Optional<User> updateUser(UUID id, User updatedUser) {
//    return userRepository.findById(id).map(user -> {
//        user.setUsername(updatedUser.getUsername());
//        user.setEmail(updatedUser.getEmail());
//        user.setPassword(updatedUser.getPassword());
//        user.setRole(updatedUser.getRole());
//        return userRepository.save(user);
//    });
//}
//
//
//public boolean deleteUser(UUID id) {
//    return userRepository.findById(id).map(user -> {
//        userRepository.delete(user);
//        return true;
//    }).orElse(false);
//}
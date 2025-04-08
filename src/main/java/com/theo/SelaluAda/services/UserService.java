package com.theo.SelaluAda.services;

import com.theo.SelaluAda.model.Role;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.repository.RoleRepository;
import com.theo.SelaluAda.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;


    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        Role role = roleService.getById(UUID.fromString("8B205EEC-32B1-4197-841E-09249ADF84DC"));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Optional<User> updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        });
    }


    public boolean deleteUser(UUID id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}

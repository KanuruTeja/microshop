package com.microShop.user.service;

import com.microShop.user.entity.User;
import com.microShop.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password Encoder

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    public User createUser(User user) {
        // Ensure password is not null or empty before saving
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Get User by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update User
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());

            // Only update password if a new one is provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return userRepository.save(updatedUser);
        }
        return null;
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}



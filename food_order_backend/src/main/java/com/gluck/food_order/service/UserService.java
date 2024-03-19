package com.gluck.food_order.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // Save user to database
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    // Get user methods
    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users", e);
        }
    }

    public User getUserById(Integer id) {
        try {
            return userRepository.getUserById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user by id", e);
        }
    }

    public User getUserByEmail(String username) {
        try {
            return userRepository.getUserByEmail(username).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user by username", e);
        }
    }

    // User delete
    public String deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
            return "User deleted with id: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    // Update user methods
    public User updateUser(User user) {
        try {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: " + user.getId() + " not found"));
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setCreatedAt(user.getCreatedAt());

            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user data", e);
        }
    }

    public void updatePassword(String password, Integer id) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: " + id + " not found"));
            existingUser.setPassword(passwordEncoder.encode(password));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password", e);
        }
    }
}

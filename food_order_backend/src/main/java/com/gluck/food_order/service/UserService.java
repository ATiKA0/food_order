package com.gluck.food_order.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gluck.food_order.dto.UserPasswordChangeDTO;
import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Update user method
    public User updateUser(User user) {
        try {
            User existingUser = userRepository.getUserById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: " + user.getId() + " not found"));
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setCreatedAt(user.getCreatedAt());
            existingUser.setAddress(user.getAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user data", e);
        }
    }

    // User password change methods
    public void updatePassword(String password, Integer id) {
        try {
            User existingUser = userRepository.getUserById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: " + id + " not found"));
            existingUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password", e);
        }
    }

    public ResponseEntity<?> changeUserPassword(UserPasswordChangeDTO userPasswordChangeDTO) {
        try {
            System.out.println(userPasswordChangeDTO.getNewPassword() + userPasswordChangeDTO.getUserId());
            updatePassword(userPasswordChangeDTO.getNewPassword(), userPasswordChangeDTO.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during password chenge");
        }
    }
}

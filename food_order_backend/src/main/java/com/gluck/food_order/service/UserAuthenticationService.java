package com.gluck.food_order.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gluck.food_order.dto.UserRegistrationResponseDTO;
import com.gluck.food_order.model.Role;
import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.UserRepository;

@Service
@Transactional
public class UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> registerUser(String firstName, String lastName, String email, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setRole(Role.USER);
        newUser.setPassword(encodedPassword);
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(newUser);

        UserRegistrationResponseDTO userRegistrationResponseDTO = new UserRegistrationResponseDTO(firstName, lastName,
                email);
        return ResponseEntity.status(HttpStatus.OK).body(userRegistrationResponseDTO);
    }
}

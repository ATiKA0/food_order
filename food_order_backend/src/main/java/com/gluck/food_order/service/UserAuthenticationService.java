package com.gluck.food_order.service;

import java.sql.Timestamp;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gluck.food_order.dto.UserLoginResponseDTO;
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

        try {
            String encodedPassword = passwordEncoder.encode(password);

            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setRole(Role.USER);
            newUser.setPassword(encodedPassword);
            newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(newUser);

            UserRegistrationResponseDTO userRegistrationResponseDTO = new UserRegistrationResponseDTO(firstName,
                    lastName,
                    email);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during user registration");
        }
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(authentication, userRepository.getUserByEmail(email).get().getId());

            UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO(
                    userRepository.getUserByEmail(email).get().getEmail(), token);

            if (userLoginResponseDTO.getEmail() == null || Objects.equals(userLoginResponseDTO.getJwt(), "")) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");

            } else {

                return ResponseEntity.status(HttpStatus.OK).body(userLoginResponseDTO);

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during user login");
        }
    }

    public Integer getUserIdFromJwt(String jwt) {

        return Integer.valueOf(tokenService.decodeJwt(jwt).getSubject());
    }
}

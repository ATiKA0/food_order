package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.dto.UserLoginDTO;
import com.gluck.food_order.dto.UserRegistrationDTO;
import com.gluck.food_order.service.UserAuthenticationService;

@RestController
@RequestMapping("/auth/user")
@CrossOrigin("*")
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserRegistrationDTO body) {
        return userAuthenticationService.registerUser(body.getFirstName(), body.getLastName(), body.getEmail(),
                body.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody UserLoginDTO body) {
        return userAuthenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}

package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.dto.UserPasswordChangeDTO;
import com.gluck.food_order.model.User;
import com.gluck.food_order.service.UserAuthenticationService;
import com.gluck.food_order.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping("/test")
    public String test() {
        return "test user level";
    }

    @GetMapping("/userdata")
    public ResponseEntity<?> GetUserData(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        Integer userId = userAuthenticationService.getUserIdFromJwt(jwtToken);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/changeUserPassword")
    public ResponseEntity<?> ChangeUserPassword(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO) {
        return userService.changeUserPassword(userPasswordChangeDTO);
    }
}

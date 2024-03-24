package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.dto.UserPasswordChangeDTO;
import com.gluck.food_order.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "test user level";
    }

    @PutMapping("/changeUserPassword")
    public ResponseEntity<?> ChangeUserPassword(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO) {
        return userService.changeUserPassword(userPasswordChangeDTO);
    }
}

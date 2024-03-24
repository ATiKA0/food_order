package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.model.Food;
import com.gluck.food_order.service.FoodService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/test")
    public String test() {
        return "test admin level";
    }

    @PostMapping("/upload-food")
    public ResponseEntity<?> UploadNewFood(@RequestBody Food newFood) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.saveFood(newFood));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during food upload");
        }
    }
}

package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.service.FoodService;

@RestController
@RequestMapping("/food")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/getAllFood")
    public ResponseEntity<?> GetAllFood() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(foodService.getAllFood());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting foods");
        }
    }

    @GetMapping("/getFoodById/{id}")
    public ResponseEntity<?> GetFoodById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(foodService.getFoodById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting food by id");
        }
    }

}

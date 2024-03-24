package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.model.Food;
import com.gluck.food_order.model.Ingredient;
import com.gluck.food_order.service.FoodService;
import com.gluck.food_order.service.IngredientService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/test")
    public String test() {
        return "test admin level";
    }

    // Food
    @PostMapping("/upload-food")
    public ResponseEntity<?> UploadNewFood(@Validated @RequestBody Food newFood, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.saveFood(newFood));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during upload food");
        }
    }

    @PutMapping("/update-food")
    public ResponseEntity<?> UpdateFood(@Validated @RequestBody Food food, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(foodService.updateFood(food));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during updating food");
        }
    }

    @DeleteMapping("/delete-food/{id}")
    public ResponseEntity<?> DeleteFood(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(foodService.deleteFood(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during delete food");
        }
    }

    // Ingredients
    @PostMapping("/upload-ingredient")
    public ResponseEntity<?> UploadNewIngredient(@Validated @RequestBody Ingredient newIngredient,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveIngredient(newIngredient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during food upload");
        }
    }

    @PutMapping("/update-ingredient")
    public ResponseEntity<?> UpdateIngredient(@Validated @RequestBody Ingredient ingredient,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientService.updateIngredient(ingredient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during updating ingredient");
        }
    }

    @DeleteMapping("/delete-ingredient/{id}")
    public ResponseEntity<?> DeleteIngredient(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientService.deleteIngredient(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting ingredient");
        }
    }
}

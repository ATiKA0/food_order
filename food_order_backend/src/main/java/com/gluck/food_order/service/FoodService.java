package com.gluck.food_order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gluck.food_order.model.Food;
import com.gluck.food_order.model.Ingredient;
import com.gluck.food_order.repository.FoodRepository;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // save food to database
    public Food saveFood(Food food) {
        try {
            return foodRepository.save(food);
        } catch (Exception e) {
            throw new RuntimeException("Error saving food", e);
        }
    }

    // Get food methods
    public List<Food> getAllFood() {
        try {
            return foodRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving foods");
        }
    }

    public Food getFoodById(Integer id) {
        try {
            return foodRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get food by ID", e);
        }
    }

    public Food getFoodByName(String name) {
        try {
            return foodRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get food by name", e);
        }
    }

    // Delete food from database
    public String deleteFood(Integer id) {
        try {
            foodRepository.deleteById(id);
            return "Food deleted with id: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete food", e);
        }
    }

    // Update food methods
    public Food updateFood(Food food) {
        try {
            Food existingFood = foodRepository.findById(food.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Food with ID: " + food.getId() + " not found"));
            existingFood.setName(food.getName());
            existingFood.setIngredients(food.getIngredients());

            return foodRepository.save(existingFood);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update food data", e);
        }
    }

    public void updateIngredients(List<Ingredient> ingredients, Integer id) {
        try {
            Food existingFood = foodRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Food with ID: " + id + " not found"));
            existingFood.setIngredients(ingredients);
            foodRepository.save(existingFood);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update ingredients", e);
        }
    }
}

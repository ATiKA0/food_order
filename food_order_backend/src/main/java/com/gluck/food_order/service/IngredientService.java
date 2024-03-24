package com.gluck.food_order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gluck.food_order.model.Ingredient;
import com.gluck.food_order.repository.IngredientRepository;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    // save food to database
    public Ingredient saveIngredient(Ingredient ingredient) {
        try {
            return ingredientRepository.save(ingredient);
        } catch (Exception e) {
            throw new RuntimeException("Error saving ingredient", e);
        }
    }

    // Get ingredient methods
    public List<Ingredient> getAllIngredient() {
        try {
            return ingredientRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving ingredients", e);
        }
    }

    public Ingredient getIngredientById(Integer id) {
        try {
            return ingredientRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ingredient by ID", e);
        }
    }

    public Ingredient getIngredientByName(String name) {
        try {
            return ingredientRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ingredient by name", e);
        }
    }

    // Delete ingredient from database
    public String deleteIngredient(Integer id) {
        try {
            ingredientRepository.deleteById(id);
            return "Ingredient deleted with id: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete ingredient", e);
        }
    }

    // Update ingredient method
    public Ingredient updateIngredient(Ingredient ingredient) {
        try {
            Ingredient existingIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Ingredient with ID: " + ingredient.getId() + " not found"));
            existingIngredient.setName(ingredient.getName());

            return ingredientRepository.save(existingIngredient);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update ingredient data", e);
        }
    }
}

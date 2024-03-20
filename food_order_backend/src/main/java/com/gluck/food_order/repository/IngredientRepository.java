package com.gluck.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gluck.food_order.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findById(Integer id);
}

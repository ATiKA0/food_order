package com.gluck.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gluck.food_order.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    Optional<Food> findByName(String name);

    Optional<Food> findById(Integer id);
}

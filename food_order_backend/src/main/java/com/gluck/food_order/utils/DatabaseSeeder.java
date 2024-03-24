package com.gluck.food_order.utils;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gluck.food_order.model.Food;
import com.gluck.food_order.model.Ingredient;
import com.gluck.food_order.model.Role;
import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.FoodRepository;
import com.gluck.food_order.repository.IngredientRepository;
import com.gluck.food_order.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private UserRepository userRepository;
    private FoodRepository foodRepository;
    private IngredientRepository ingredientRepository;
    private PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, FoodRepository foodRepository,
            IngredientRepository ingredientRepository, PasswordEncoder passwordEncoder) {
        this.foodRepository = foodRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createUser();
        createAdmin();
        addIngredients();
        addFoods();
    }

    private void createAdmin() {

        User admin = new User();
        admin.setFirstName("AdminF");
        admin.setLastName("AdminL");
        admin.setEmail("admin@email.com");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(admin);
    }

    private void createUser() {

        User user = new User();
        user.setFirstName("UserF");
        user.setLastName("UserL");
        user.setEmail("user@email.com");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRole(Role.USER);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);
    }

    private void addIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("testIngredient");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("testIngredient2");
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("testIngredient3");
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
    }

    private void addFoods() {
        Ingredient ingredient1 = ingredientRepository.findByName("testIngredient").get();
        Ingredient ingredient2 = ingredientRepository.findByName("testIngredient2").get();
        Ingredient ingredient3 = ingredientRepository.findByName("testIngredient3").get();

        Food food1 = new Food();
        food1.setName("testFood1");
        food1.setIngredients(List.of(ingredient1, ingredient2));
        foodRepository.save(food1);
        Food food2 = new Food();
        food2.setName("testFood2");
        food2.setIngredients(List.of(ingredient3));
        foodRepository.save(food2);

    }

}

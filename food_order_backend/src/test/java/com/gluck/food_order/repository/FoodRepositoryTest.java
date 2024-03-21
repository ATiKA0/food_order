package com.gluck.food_order.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.gluck.food_order.model.Food;
import com.gluck.food_order.model.Ingredient;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = { "classpath:application-test.properties" }, properties = {
        "spring.config.name=application-test" })
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void FoodRepository_Save_ReturnSavedFood() {

        // Arrange
        Food testFood = new Food();
        testFood.setName("TEST");
        Ingredient ingredient = new Ingredient();
        ingredient.setName("test1");
        Ingredient ingredient2 = new Ingredient();
        ingredient.setName("test2");
        testFood.setIngredients(List.of(ingredient, ingredient2));

        // Act
        Food savedFood = foodRepository.save(testFood);

        // Assert
        Assertions.assertNotNull(savedFood);
        Assertions.assertTrue(savedFood.getId() > 0);
        Assertions.assertEquals("TEST", savedFood.getName());
        Assertions.assertEquals(testFood.getIngredients(), savedFood.getIngredients());
    }

    @Test
    public void FoodRepository_GetAll_ReturnsMoreThenOne() {

        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("test1");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("test2");
        Food testFood = new Food();
        testFood.setName("TEST");
        testFood.setIngredients(List.of(ingredient, ingredient2));
        Food testFood2 = new Food();
        testFood2.setName("TEST2");
        testFood2.setIngredients(List.of(ingredient, ingredient2));

        foodRepository.save(testFood);
        foodRepository.save(testFood2);

        // Act
        List<Food> foodList = foodRepository.findAll();

        // Assert
        Assertions.assertNotNull(foodList);
        Assertions.assertEquals(foodList.size(), 2);
    }

    @Test
    public void FoodRepository_GetFoodByName_ReturnFood() {

        // Arrange
        Food testFood = new Food();
        testFood.setName("TEST");
        foodRepository.save(testFood);

        // Act
        Optional<Food> foundFood = foodRepository.findByName("TEST");

        // Assert
        Assertions.assertNotNull(foundFood);
        Assertions.assertEquals(foundFood.get().getName(), "TEST");
    }

    @Test
    public void FoodRepository_GetFoodById_ReturnOptionalEmptyIfNotFound() {
        Optional<Food> notFoundFood = foodRepository.findById(4);

        // Assert
        Assertions.assertEquals(notFoundFood, Optional.empty());

    }
}

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
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void IngredientRepository_Save_ReturnSavedIngredient() {

        // Arrange
        Ingredient testIngredient = new Ingredient();
        testIngredient.setName("TEST");
        Food food = new Food();
        food.setName("test1");
        Food food2 = new Food();
        food2.setName("test2");
        testIngredient.setFood(List.of(food, food2));

        // Act
        Ingredient savedIngredient = ingredientRepository.save(testIngredient);

        // Assert
        Assertions.assertNotNull(savedIngredient);
        Assertions.assertTrue(savedIngredient.getId() > 0);
        Assertions.assertEquals("TEST", savedIngredient.getName());
        Assertions.assertEquals(testIngredient.getFood(), savedIngredient.getFood());
    }

    @Test
    public void IngredientRepository_GetAll_ReturnsMoreThenOne() {

        // Arrange
        Food testFood = new Food();
        testFood.setName("TEST");
        Food testFood2 = new Food();
        testFood2.setName("TEST2");
        Ingredient ingredient = new Ingredient();
        ingredient.setName("test1");
        ingredient.setFood(List.of(testFood, testFood2));
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("test2");
        ingredient2.setFood(List.of(testFood, testFood2));

        ingredientRepository.save(ingredient);
        ingredientRepository.save(ingredient2);

        // Act
        List<Ingredient> ingredientList = ingredientRepository.findAll();

        // Assert
        Assertions.assertNotNull(ingredientList);
        Assertions.assertEquals(ingredientList.size(), 2);
    }

    @Test
    public void IngredientRepository_GetIngredientByName_ReturnIngredient() {

        // Arrange
        Ingredient testIngredient = new Ingredient();
        testIngredient.setName("TEST");
        ingredientRepository.save(testIngredient);

        // Act
        Optional<Ingredient> foundIngredient = ingredientRepository.findByName("TEST");

        // Assert
        Assertions.assertNotNull(foundIngredient);
        Assertions.assertEquals(foundIngredient.get().getName(), "TEST");
    }

    @Test
    public void IngredientRepository_GetIngredientById_ReturnOptionalEmptyIfNotFound() {

        // Act
        Optional<Ingredient> notFoundIngredient = ingredientRepository.findById(4);

        // Assert
        Assertions.assertEquals(notFoundIngredient, Optional.empty());
    }
}

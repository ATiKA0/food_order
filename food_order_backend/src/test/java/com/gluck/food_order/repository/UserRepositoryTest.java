package com.gluck.food_order.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.gluck.food_order.model.Role;
import com.gluck.food_order.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = { "classpath:application-test.properties" }, properties = {
        "spring.config.name=application-test" })
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Save_ReturnSavedUser() {

        // Arrange
        User testUser = new User();
        testUser.setFirstName("TEST");
        testUser.setLastName("TEST");
        testUser.setEmail("TEST");
        testUser.setPassword("TEST");
        testUser.setRole(Role.USER);
        testUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId().intValue() > 0);
        Assertions.assertEquals("TEST", savedUser.getFirstName());
        Assertions.assertEquals("TEST", savedUser.getLastName());
        Assertions.assertEquals("TEST", savedUser.getEmail());
        Assertions.assertEquals("TEST", savedUser.getPassword());
        Assertions.assertEquals(Role.USER, savedUser.getRole());
        Assertions.assertEquals(testUser.getCreatedAt(), savedUser.getCreatedAt());
    }

    @Test
    public void UserRepository_GetAll_ReturnsMoreThenOne() {

        // Arrange
        User user = User.builder().firstName("TEST").lastName("TEST")
                .email("TEST").password("TEST").role(Role.USER)
                .createdAt(new Timestamp(System.currentTimeMillis())).build();

        User user2 = User.builder().firstName("TEST2").lastName("TEST2")
                .email("TEST2").password("TEST2").role(Role.USER)
                .createdAt(new Timestamp(System.currentTimeMillis())).build();

        userRepository.save(user);
        userRepository.save(user2);
        // Act
        List<User> userList = userRepository.findAll();

        // Assert
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 2);
    }

    @Test
    public void UserRepository_GetUserByEmail_ReturnUser() {
        // Arrange
        User testUser = User.builder().email("TEST").build();
        userRepository.save(testUser);

        // Act
        Optional<User> foundUser = userRepository.getUserByEmail("TEST");

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(foundUser.get().getUsername(), "TEST");
    }

    @Test
    public void UserRepository_GetUserById_ReturnOptionalEmptyIfNotFound() {
        Optional<User> notFoundUser = userRepository.findById(4);

        // Assert
        Assertions.assertEquals(notFoundUser, Optional.empty());

    }
}

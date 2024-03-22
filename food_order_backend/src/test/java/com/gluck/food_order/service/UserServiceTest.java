package com.gluck.food_order.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void UserService_GetUserByEmail_ReturnUser() {
        User testUser = new User();
        testUser.setEmail("TEST");

        when(userRepository.getUserByEmail(Mockito.any(String.class))).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserByEmail("TEST");

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("TEST", foundUser.getEmail());
    }

    @Test
    public void UserService_GetUserById_ReturnUser() {

        User testUser = new User();
        testUser.setId(6);

        when(userRepository.getUserById(Mockito.any(Integer.class))).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserById(6);

        Assertions.assertEquals(6, foundUser.getId());
    }

    @Test
    public void UserService_GetUserById_ReturnNullIfNotFound() {

        User notFoundUser = userService.getUserById(4);

        Assertions.assertNull(notFoundUser);
    }

    @Test
    public void UserService_GetUserByEmail_ReturnNullIfNotFound() {

        User notFoundUser = userService.getUserByEmail("TEST");

        Assertions.assertNull(notFoundUser);
    }

}

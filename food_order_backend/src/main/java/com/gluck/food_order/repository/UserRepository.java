package com.gluck.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gluck.food_order.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Integer id);
}

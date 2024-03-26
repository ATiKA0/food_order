package com.gluck.food_order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gluck.food_order.model.Order;
import com.gluck.food_order.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOrderById(Integer id);

    Optional<Order> getOrderByUser(User user);
}

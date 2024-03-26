package com.gluck.food_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gluck.food_order.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}

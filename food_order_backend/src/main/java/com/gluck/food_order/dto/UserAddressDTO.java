package com.gluck.food_order.dto;

import com.gluck.food_order.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAddressDTO {

    private Integer id;
    private Address address;
}

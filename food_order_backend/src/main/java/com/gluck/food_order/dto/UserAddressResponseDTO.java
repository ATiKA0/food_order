package com.gluck.food_order.dto;

import java.util.List;

import com.gluck.food_order.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAddressResponseDTO {

    private List<Address> addressList;
}

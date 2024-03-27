package com.gluck.food_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAddressUpdateDTO {
    private Integer userId;
    private Integer addressId;
    private Integer zipCode;
    private String country;
    private String county;
    private String street;
    private Boolean isDefault;
}

package com.gluck.food_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

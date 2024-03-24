package com.gluck.food_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPasswordChangeDTO {

    private Integer userId;
    private String newPassword;
}

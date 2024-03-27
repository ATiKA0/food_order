package com.gluck.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluck.food_order.dto.UserAddressDTO;
import com.gluck.food_order.dto.UserAddressUpdateDTO;
import com.gluck.food_order.service.AddressService;

@RestController
@CrossOrigin("*")
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/upload-address")
    public ResponseEntity<?> UploadAddress(@RequestBody UserAddressDTO address,
            BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
            return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addNewAddress(address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during upload address");
        }
    }

    @PutMapping("/update-address")
    public ResponseEntity<?> UpdateAddress(@Validated @RequestBody UserAddressUpdateDTO address,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request");
            return ResponseEntity.status(HttpStatus.OK).body(addressService.updateAddressByUserId(address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during upload address");
        }
    }

    @DeleteMapping("/delete-address/{userId}/{addressId}")
    public ResponseEntity<?> DeleteAddress(@PathVariable Integer userId, @PathVariable Integer addressId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(addressService.removeAddressById(userId, addressId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during upload address");
        }
    }
}

package com.gluck.food_order.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gluck.food_order.dto.UserAddressDTO;
import com.gluck.food_order.dto.UserAddressResponseDTO;
import com.gluck.food_order.dto.UserAddressUpdateDTO;
import com.gluck.food_order.model.Address;
import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.AddressRepository;
import com.gluck.food_order.repository.UserRepository;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public UserAddressResponseDTO addNewAddress(UserAddressDTO newAddress) {
        try {
            User user = userRepository.getUserById(newAddress.getUserId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "User with ID: " + newAddress.getUserId() + " not found"));
            List<Address> existingAddressList = user.getAddress();
            existingAddressList.add(new Address(null, user, newAddress.getZipCode(), newAddress.getCountry(),
                    newAddress.getCounty(), newAddress.getStreet(), newAddress.getIsDefault()));
            user.setAddress(existingAddressList);
            userRepository.save(user);
            return new UserAddressResponseDTO(existingAddressList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save address", e);
        }
    }

    public UserAddressResponseDTO updateAddressByUserId(UserAddressUpdateDTO addresstoUpdate) {
        try {
            User user = userRepository.getUserById(addresstoUpdate.getUserId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "User with ID: " + addresstoUpdate.getUserId() + " not found"));
            List<Address> existingAddressList = user.getAddress();
            removeAddressFromListById(addresstoUpdate.getAddressId(), existingAddressList);
            existingAddressList.add(new Address(addresstoUpdate.getAddressId(), user, addresstoUpdate.getZipCode(),
                    addresstoUpdate.getCountry(),
                    addresstoUpdate.getCounty(), addresstoUpdate.getStreet(), addresstoUpdate.getIsDefault()));
            user.setAddress(existingAddressList);
            userRepository.save(user);

            return new UserAddressResponseDTO(user.getAddress());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update address with ID: " + addresstoUpdate.getAddressId(), e);
        }
    }

    public UserAddressResponseDTO removeAddressById(Integer userId, Integer addressId) {
        try {
            User user = userRepository.getUserById(userId)
                    .orElseThrow(
                            () -> new IllegalArgumentException("User with ID: " + userId + " not found"));
            addressRepository.deleteById(addressId);
            List<Address> existingAddressList = user.getAddress();
            return new UserAddressResponseDTO(existingAddressList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete food", e);
        }
    }

    public void removeAddressFromListById(Integer id, List<Address> list) {
        Iterator<Address> iterator = list.iterator();
        while (iterator.hasNext()) {
            Address address = iterator.next();
            if (address.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}

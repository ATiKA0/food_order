package com.gluck.food_order.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gluck.food_order.dto.UserAddressDTO;
import com.gluck.food_order.dto.UserAddressResponseDTO;
import com.gluck.food_order.model.Address;
import com.gluck.food_order.model.User;
import com.gluck.food_order.repository.UserRepository;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    public UserAddressResponseDTO addNewAddress(UserAddressDTO addressDTO) {
        try {
            User user = userRepository.getUserById(addressDTO.getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("User with ID: " + addressDTO.getId() + " not found"));
            List<Address> existingAddressList = user.getAddress();
            Address newAddress = addressDTO.getAddress();
            newAddress.setUser(user);
            existingAddressList.add(newAddress);
            user.setAddress(existingAddressList);
            userRepository.save(user);
            return new UserAddressResponseDTO(existingAddressList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save address", e);
        }
    }

    public UserAddressResponseDTO updateAddressByUserId(UserAddressDTO addressDTO) {
        try {
            User user = userRepository.getUserById(addressDTO.getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("User with ID: " + addressDTO.getId() + " not found"));
            List<Address> existingAddressList = user.getAddress();
            removeAddressFromListById(addressDTO.getId(), existingAddressList);
            existingAddressList.add(addressDTO.getAddress());
            user.setAddress(existingAddressList);
            userRepository.save(user);
            return new UserAddressResponseDTO(existingAddressList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update address with ID: " + addressDTO.getId(), e);
        }
    }

    public UserAddressResponseDTO removeAddressById(Integer userId, Integer addressId) {
        try {
            User user = userRepository.getUserById(userId)
                    .orElseThrow(
                            () -> new IllegalArgumentException("User with ID: " + userId + " not found"));
            List<Address> existingAddressList = user.getAddress();
            removeAddressFromListById(addressId, existingAddressList);
            user.setAddress(existingAddressList);
            userRepository.save(user);
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

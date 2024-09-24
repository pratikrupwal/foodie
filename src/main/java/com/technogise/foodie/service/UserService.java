package com.technogise.foodie.service;

import com.technogise.foodie.dto.UserRegistrationDto;
import com.technogise.foodie.model.User;
import com.technogise.foodie.model.User.UserType;
import com.technogise.foodie.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        if (userRepository.findByPhoneNumber(registrationDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number is already in use");
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setPasswordHash(registrationDto.getPassword());

        try {
            user.setUserType(UserType.valueOf(registrationDto.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid user type. Allowed values: FOODIE, RESTAURANT_OWNER, DELIVERY_PARTNER");
        }

        return userRepository.save(user);
    }

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}

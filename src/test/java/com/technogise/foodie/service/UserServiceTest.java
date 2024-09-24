package com.technogise.foodie.service;

import com.technogise.foodie.model.User;
import com.technogise.foodie.model.User.UserType;
import com.technogise.foodie.repository.UserRepository;
import com.technogise.foodie.dto.UserRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerNewUser_shouldSaveUserAndReturnIt() {
        // Arrange
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("Pratik Rupwal","pratik@example.com","password123","1234567890","FOODIE");
        
        User user = new User();
        user.setUserId(1L);
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setPasswordHash("hashedPassword"); // Assuming password is hashed before saving
        user.setUserType(UserType.FOODIE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.registerNewUser(userRegistrationDto);

        // Assert
        assertNotNull(savedUser);
        assertEquals("Pratik Rupwal", savedUser.getName());
        assertEquals("pratik@example.com", savedUser.getEmail());
        assertEquals("1234567890", savedUser.getPhoneNumber());
        assertEquals(UserType.FOODIE, savedUser.getUserType());

        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void registerNewUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("existing@example.com");
        userRegistrationDto.setPhoneNumber("1234567890");
        userRegistrationDto.setUserType("FOODIE");

        // Mock that an existing user already has this email
        when(userRepository.existsByEmail(userRegistrationDto.getEmail())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerNewUser(userRegistrationDto);
        });

        assertEquals("Email already in use", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(userRegistrationDto.getEmail());
        verify(userRepository, never()).existsByPhoneNumber(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerNewUser_shouldThrowExceptionWhenPhoneNumberAlreadyExists() {
        // Arrange
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("newuser@example.com");
        userRegistrationDto.setPhoneNumber("9876543210");
        userRegistrationDto.setUserType("DELIVERY_PARTNER");

        // Mock that an existing user already has this phone number
        when(userRepository.existsByEmail(userRegistrationDto.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(userRegistrationDto.getPhoneNumber())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerNewUser(userRegistrationDto);
        });

        assertEquals("Phone number already in use", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(userRegistrationDto.getEmail());
        verify(userRepository, times(1)).existsByPhoneNumber(userRegistrationDto.getPhoneNumber());
        verify(userRepository, never()).save(any(User.class));
    }
}
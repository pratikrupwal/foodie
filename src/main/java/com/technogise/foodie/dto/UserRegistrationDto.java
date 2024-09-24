package com.technogise.foodie.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class UserRegistrationDto {

    @NotBlank(message = "Name is required")
    @Getter
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Getter
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 characters")
    @Getter
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotBlank(message = "User type is required")
    private String userType; // This should match one of the ENUM values: FOODIE, RESTAURANT_OWNER, DELIVERY_PARTNER
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@JsonCreator
    public UserRegistrationDto(@JsonProperty("name") String name,
    		@JsonProperty("email") String email,
    		@JsonProperty("password") String password,
    		@JsonProperty("phoneNumber") String phoneNumber,
    		@JsonProperty("userType") String userType) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.phoneNumber = phoneNumber;
    	this.userType = userType;
    }
}
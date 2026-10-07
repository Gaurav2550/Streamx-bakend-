package com.oid.streamxbackend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Username is required")
        @Size(min = 3 , max = 50 , message = "User name must be between 3 to 50 characters")
        String username
,
        @NotBlank(message = "Email is  required")
         @Email(message = "Invalid email format")
        String email
,
        @NotBlank(message = "password is required")
        @Size(min = 8, max = 100 , message = "Password must be between 8 and 100 characters")
        String password


) {



}

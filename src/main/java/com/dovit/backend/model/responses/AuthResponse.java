package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Payload to response the Sign In request
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String tokenType="Bearer";
    private String name;
    private String lastName;
    private String role;
    private String company;

    public AuthResponse(String accessToken, String name, String lastName, String role, String company) {
        this.accessToken = accessToken;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.company = company;
    }
}

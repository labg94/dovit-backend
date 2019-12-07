package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Authentication request for sign in
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "Email no puede estar vacío")
    @Email
    private String email;

    @NotEmpty(message = "Contraseña no puede estar vacío")
    private String password;

}

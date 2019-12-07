package com.dovit.backend.model.requests;

import com.dovit.backend.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.util.List;

/**
 * This class is for the sign up of the client only
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "Token no debe estar vacío")
    private String registrationToken;

    @NotBlank(message = "Nombre no debe estar vacío")
    private String name;

    @NotBlank(message = "Apellido no debe estar vacío")
    private String lastName;

    @NotBlank(message = "Password no debe estar vacío")
    @Size(message = "Contraseña inválida", min = 6, max = 20)
    private String password;



}

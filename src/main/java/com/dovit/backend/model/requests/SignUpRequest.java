package com.dovit.backend.model.requests;

import com.dovit.backend.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Nullable
    private Long id;

    @NotBlank(message = "Nombre no debe estar vacío")
    private String name;

    @NotBlank(message = "Apellido no debe estar vacío")
    private String lastName;

    @NotBlank(message = "Nombre de usuario no debe estar vacío")
    @Email
    private String email;

    @NotBlank(message = "Password no debe estar vacío")
    @Size(message = "Contraseña inválida", min = 6, max = 20)
    private String password;

    @NotNull
    private Boolean active;

    @NotNull(message = "Debe seleccionar al menos un rol")
    private Long roleId;

    private Long companyId;

}

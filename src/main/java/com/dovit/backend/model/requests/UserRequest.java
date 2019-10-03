package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * To register new users from the administrator site. It can create / update any type of users
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest  {

    @Nullable
    private Long id;

    @NotBlank(message = "Nombre no debe estar vacío")
    private String name;

    @NotBlank(message = "Apellido no debe estar vacío")
    private String lastName;

    @NotBlank(message = "Correo electrónico no debe estar vacío")
    @Email
    private String email;

    private String password;

    @NotNull
    private Boolean active;

    @NotNull(message = "Debe seleccionar al menos un rol")
    private Long roleId;

    private Long companyId;


}

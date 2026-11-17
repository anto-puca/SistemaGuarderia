package com.guarderia.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmpleadoCreateDTO(
        @NotBlank String nombre,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String codigo,
        @NotBlank String especialidad
) {}

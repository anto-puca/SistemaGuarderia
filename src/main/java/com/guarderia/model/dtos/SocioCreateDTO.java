package com.guarderia.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SocioCreateDTO(
        @NotBlank String nombre,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String dni,
        @NotNull LocalDate fechaIngreso
) {}
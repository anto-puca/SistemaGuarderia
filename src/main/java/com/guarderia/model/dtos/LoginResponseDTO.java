package com.guarderia.model.dtos;

public record LoginResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol // "ADMIN", "SOCIO" o "EMPLEADO"
) {}
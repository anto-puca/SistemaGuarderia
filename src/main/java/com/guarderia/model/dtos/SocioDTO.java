package com.guarderia.model.dtos;

import com.guarderia.model.entities.Socio;
import java.time.LocalDate;

public record SocioDTO(
        Long id,
        String nombre,
        String direccion,
        String telefono,
        String email,
        String dni,
        LocalDate fechaIngreso
) {
    // Constructor de mapeo rápido Entity -> DTO (sin password)
    public SocioDTO(Socio s) {
        this(s.getId(), s.getNombre(), s.getDireccion(), s.getTelefono(), s.getEmail(), s.getDni(), s.getFechaIngreso());
    }
}
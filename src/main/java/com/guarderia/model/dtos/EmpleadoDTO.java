package com.guarderia.model.dtos;

import com.guarderia.model.entities.Empleado;

public record EmpleadoDTO(
        Long id,
        String nombre,
        String direccion,
        String telefono,
        String email,
        String codigo,
        String especialidad
) {
    public EmpleadoDTO(Empleado e) {
        this(e.getId(), e.getNombre(), e.getDireccion(), e.getTelefono(), e.getEmail(), e.getCodigo(), e.getEspecialidad());
    }
}

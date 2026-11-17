package com.guarderia.model.dtos;

import com.guarderia.model.entities.Vehiculo;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record VehiculoDTO(
        @NotBlank String matricula,
        @NotBlank String nombre,
        @NotBlank String tipo,
        @NotBlank String dimensiones,
        @NotNull Long socioId,
        @NotNull Integer numeroGarage,
        LocalDate fechaAsignacion
) {
    public VehiculoDTO(Vehiculo v) {
        this(
                v.getMatricula(),
                v.getNombre(),
                v.getTipo(),
                v.getDimensiones(),
                v.getSocio().getId(),
                v.getGarage() != null ? v.getGarage().getNumeroGarage() : null,
                v.getGarage() != null ? v.getGarage().getFechaAsignacionVehiculo() : null
        );
    }
}

package com.guarderia.model.dtos;

import com.guarderia.model.entities.Garage;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GarageDTO(
        @NotNull Integer numeroGarage,
        @NotNull Double lecturaContadorLuz,
        @NotNull Boolean tieneMantenimiento,
        LocalDate fechaAsignacionVehiculo,
        LocalDate fechaCompraSocio,
        @NotNull String zonaLetra,
        Long socioPropietarioId,
        String vehiculoMatricula
) {
    public GarageDTO(Garage g) {
        this(
                g.getNumeroGarage(),
                g.getLecturaContadorLuz(),
                g.getTieneMantenimiento(),
                g.getFechaAsignacionVehiculo(),
                g.getFechaCompraSocio(),
                g.getZona().getLetra(),
                g.getPropietario() != null ? g.getPropietario().getId() : null,
                g.getVehiculo() != null ? g.getVehiculo().getMatricula() : null
        );
    }
}

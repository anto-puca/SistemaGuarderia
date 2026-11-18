package com.guarderia.model.dtos;

import com.guarderia.model.entities.AsignacionEmpleadoZona;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AsignacionDTO(
        @NotNull(message = "El ID del empleado es obligatorio")
        Long empleadoId,

        String nombreEmpleado,

        @NotBlank(message = "La letra de la zona es obligatoria")
        String zonaLetra,

        String tipoVehiculosZona,

        @NotNull(message = "La cantidad de vehículos es obligatoria")
        @Min(value = 0, message = "La cantidad no puede ser negativa")
        Integer cantidadVehiculosACargo
) {
    public AsignacionDTO(AsignacionEmpleadoZona a) {
        this(
                a.getEmpleado().getId(),
                a.getEmpleado().getNombre(),
                a.getZona().getLetra(),
                a.getZona().getTipoVehiculos(),
                a.getCantidadVehiculosACargo()
        );
    }
}

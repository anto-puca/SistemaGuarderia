package com.guarderia.model.dtos;

import com.guarderia.model.entities.AsignacionEmpleadoZona;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AsignacionDTO(
        @NotNull Long empleadoId,
        String nombreEmpleado,
        @NotNull String zonaLetra,
        String tipoVehiculosZona,
        @NotNull @Min(0) Integer cantidadVehiculosACargo
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

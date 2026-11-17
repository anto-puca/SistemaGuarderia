package com.guarderia.model.dtos;

import com.guarderia.model.entities.Zona;
import jakarta.validation.constraints.*;

public record ZonaDTO(
        @NotNull String letra,
        @NotBlank String tipoVehiculos,
        @NotNull Integer numeroVehiculos,
        @NotNull Double profundidadGarages,
        @NotNull Double anchoGarages
) {
    public ZonaDTO(Zona z) {
        this(z.getLetra(), z.getTipoVehiculos(), z.getNumeroVehiculos(), z.getProfundidadGarages(), z.getAnchoGarages());
    }
}

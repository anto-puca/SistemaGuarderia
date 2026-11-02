package com.guarderia.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class EmpleadoZonaId implements Serializable {
    private Long empleadoId;
    private String zonaId;
}
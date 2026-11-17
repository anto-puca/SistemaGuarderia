package com.guarderia.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// En AsignacionId.java
@Embeddable
public class AsignacionId implements Serializable {
    @Column(name = "empleado_id")
    private Long empleadoId;

    @Column(name = "zona_letra", length = 1)
    private String zonaLetra;

    public AsignacionId() {}
    public AsignacionId(Long empleadoId, String zonaLetra) {
        this.empleadoId = empleadoId;
        this.zonaLetra = zonaLetra;
    }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }
    public String getZonaLetra() { return zonaLetra; }
    public void setZonaLetra(String zonaLetra) { this.zonaLetra = zonaLetra; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignacionId that)) return false;
        return Objects.equals(empleadoId, that.empleadoId) && Objects.equals(zonaLetra, that.zonaLetra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empleadoId, zonaLetra);
    }
}

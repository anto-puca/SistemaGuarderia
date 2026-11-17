package com.guarderia.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_empleado_zona")
public class AsignacionEmpleadoZona {

    @EmbeddedId
    private AsignacionId id = new AsignacionId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empleadoId")
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("zonaLetra")
    @JoinColumn(name = "zona_letra")
    private Zona zona;

    @Column(name = "cantidad_vehiculos_a_cargo", nullable = false)
    private Integer cantidadVehiculosACargo;

    public AsignacionEmpleadoZona() {
    }

    public AsignacionEmpleadoZona(Empleado empleado, Zona zona, Integer cantidadVehiculosACargo) {
        this.empleado = empleado;
        this.zona = zona;
        this.cantidadVehiculosACargo = cantidadVehiculosACargo;
        this.id = new AsignacionId(empleado.getId(), zona.getLetra());
    }

    public AsignacionId getId() {
        return id;
    }

    public void setId(AsignacionId id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Integer getCantidadVehiculosACargo() {
        return cantidadVehiculosACargo;
    }

    public void setCantidadVehiculosACargo(Integer cantidadVehiculosACargo) {
        this.cantidadVehiculosACargo = cantidadVehiculosACargo;
    }
}

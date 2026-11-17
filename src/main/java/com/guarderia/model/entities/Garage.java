package com.guarderia.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "garages")
public class Garage {

    @Id
    @Column(name = "numero_garage")
    private Integer numeroGarage;

    @Column(name = "lectura_contador_luz", nullable = false)
    private Double lecturaContadorLuz;

    @Column(name = "tiene_mantenimiento", nullable = false)
    private Boolean tieneMantenimiento;

    @Column(name = "fecha_asignacion_vehiculo")
    private LocalDate fechaAsignacionVehiculo;

    @Column(name = "fecha_compra_socio")
    private LocalDate fechaCompraSocio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_letra", nullable = false)
    private Zona zona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_propietario_id")
    private Socio propietario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_matricula", unique = true)
    private Vehiculo vehiculo;

    public Garage() {
    }

    public Integer getNumeroGarage() {
        return numeroGarage;
    }

    public void setNumeroGarage(Integer numeroGarage) {
        this.numeroGarage = numeroGarage;
    }

    public Double getLecturaContadorLuz() {
        return lecturaContadorLuz;
    }

    public void setLecturaContadorLuz(Double lecturaContadorLuz) {
        this.lecturaContadorLuz = lecturaContadorLuz;
    }

    public Boolean getTieneMantenimiento() {
        return tieneMantenimiento;
    }

    public void setTieneMantenimiento(Boolean tieneMantenimiento) {
        this.tieneMantenimiento = tieneMantenimiento;
    }

    public LocalDate getFechaAsignacionVehiculo() {
        return fechaAsignacionVehiculo;
    }

    public void setFechaAsignacionVehiculo(LocalDate fechaAsignacionVehiculo) {
        this.fechaAsignacionVehiculo = fechaAsignacionVehiculo;
    }

    public LocalDate getFechaCompraSocio() {
        return fechaCompraSocio;
    }

    public void setFechaCompraSocio(LocalDate fechaCompraSocio) {
        this.fechaCompraSocio = fechaCompraSocio;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Socio getPropietario() {
        return propietario;
    }

    public void setPropietario(Socio propietario) {
        this.propietario = propietario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}

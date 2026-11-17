package com.guarderia.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zonas")
public class Zona {

    @Id
    @Column(length = 1)
    private String letra;

    @Column(name = "tipo_vehiculos", nullable = false)
    private String tipoVehiculos;

    @Column(name = "numero_vehiculos", nullable = false)
    private Integer numeroVehiculos;

    @Column(name = "profundidad_garages", nullable = false)
    private Double profundidadGarages;

    @Column(name = "ancho_garages", nullable = false)
    private Double anchoGarages;

    // Una zona contiene muchos garages (1 a N)
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Garage> garages = new ArrayList<>();

    // Relación N:M con Empleados resuelta mediante la entidad intermedia
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsignacionEmpleadoZona> empleadosAsignados = new ArrayList<>();

    // Constructor vacío obligatorio para Hibernate
    public Zona() {
    }

    // Constructor parametrizado
    public Zona(String letra, String tipoVehiculos, Integer numeroVehiculos, Double profundidadGarages, Double anchoGarages) {
        this.letra = letra;
        this.tipoVehiculos = tipoVehiculos;
        this.numeroVehiculos = numeroVehiculos;
        this.profundidadGarages = profundidadGarages;
        this.anchoGarages = anchoGarages;
    }

    // Getters y Setters explícitos


    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getTipoVehiculos() {
        return tipoVehiculos;
    }

    public void setTipoVehiculos(String tipoVehiculos) {
        this.tipoVehiculos = tipoVehiculos;
    }

    public Integer getNumeroVehiculos() {
        return numeroVehiculos;
    }

    public void setNumeroVehiculos(Integer numeroVehiculos) {
        this.numeroVehiculos = numeroVehiculos;
    }

    public Double getProfundidadGarages() {
        return profundidadGarages;
    }

    public void setProfundidadGarages(Double profundidadGarages) {
        this.profundidadGarages = profundidadGarages;
    }

    public Double getAnchoGarages() {
        return anchoGarages;
    }

    public void setAnchoGarages(Double anchoGarages) {
        this.anchoGarages = anchoGarages;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public List<AsignacionEmpleadoZona> getEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public void setEmpleadosAsignados(List<AsignacionEmpleadoZona> empleadosAsignados) {
        this.empleadosAsignados = empleadosAsignados;
    }
}

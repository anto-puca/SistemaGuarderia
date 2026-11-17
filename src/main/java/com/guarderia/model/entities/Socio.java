package com.guarderia.model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SOCIO")
public class Socio extends Usuario {

    @Column(unique = true)
    private String dni;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    // Un socio posee varios vehículos (1 a N)
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos = new ArrayList<>();

    // Un socio puede ser propietario de varios garages (1 a N opcional)
    @OneToMany(mappedBy = "propietario")
    private List<Garage> garagesComprados = new ArrayList<>();

    // Constructor vacío obligatorio para Hibernate
    public Socio() {
        super();
    }

    // Constructor parametrizado
    public Socio(String nombre, String direccion, String telefono, String email, String password, String dni, LocalDate fechaIngreso) {
        super(nombre, direccion, telefono, email, password);
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters y Setters explícitos (sin @Data)
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Garage> getGaragesComprados() {
        return garagesComprados;
    }

    public void setGaragesComprados(List<Garage> garagesComprados) {
        this.garagesComprados = garagesComprados;
    }
}

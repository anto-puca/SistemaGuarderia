package com.guarderia.model.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("EMPLEADO")
public class Empleado extends Usuario {

    @Column(unique = true)
    private String codigo;

    private String especialidad;

    // Un empleado puede estar asignado a varias zonas (N:M resuelta mediante entidad intermedia)
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsignacionEmpleadoZona> asignaciones = new ArrayList<>();

    // Constructor vacío obligatorio para Hibernate
    public Empleado() {
        super();
    }

    // Constructor parametrizado
    public Empleado(String nombre, String direccion, String telefono, String email, String password, String codigo, String especialidad) {
        super(nombre, direccion, telefono, email, password);
        this.codigo = codigo;
        this.especialidad = especialidad;
    }

    // Getters y Setters explícitos (sin @Data)
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<AsignacionEmpleadoZona> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionEmpleadoZona> asignaciones) {
        this.asignaciones = asignaciones;
    }
}

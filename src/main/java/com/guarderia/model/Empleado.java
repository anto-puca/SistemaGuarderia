package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "usuario_id") // Sincroniza la PK/FK con la tabla usuarios
@Data
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Usuario {

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    // Relación bidireccional hacia la entidad intermedia (Muchos a Muchos con atributos)
    // El 'mappedBy' apunta al campo 'empleado' dentro de la clase EmpleadoZona
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpleadoZona> zonasAsignadas;
}

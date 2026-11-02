package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "socios")
@PrimaryKeyJoinColumn(name = "usuario_id") // Une la PK/FK con Usuario
@Data
@EqualsAndHashCode(callSuper = true)
public class Socio extends Usuario {

    @Column(nullable = false, length = 20)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    // Relación socio -> vehiculos (1 a Muchos)
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

    // Relación socio -> garages (1 a Muchos)
    @OneToMany(mappedBy = "socio")
    private List<Garage> garages;
}


package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "zonas")
@Data
@NoArgsConstructor
public class Zona {

    @Id
    @Column(length = 1)
    private String letra; // Clave Primaria (PK) - Letra que define la zona (A, B, C, D)

    @Column(name = "tipo_vehiculo", length = 100, nullable = false)
    private String tipoVehiculo; // Motorhomes, caravanas, trailers, etc.

    @Column(name = "cantidad_vehiculos", nullable = false)
    private Integer cantidadVehiculos = 0; // Número de vehículos que contiene actualmente

    @Column(name = "profundidad_garages", nullable = false)
    private Double profundidadGarages;

    @Column(name = "ancho_garages", nullable = false)
    private Double anchoGarages;

    // Relación inversa: Una zona contiene muchos garages
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Garage> garages;

    // Relación inversa Many-to-Many hacia la tabla intermedia con atributos
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpleadoZona> empleadosAsignados;
}

package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad que representa la tabla "vehiculos" en la base de datos MySQL.
 * Mapea motorhomes, casas rodantes de arrastre, caravanas y trailers.
 */
@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @Column(length = 20, nullable = false)
    private String matricula; // Clave Primaria (PK) directa según la consigna

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String tipo; // Motorhome, Casa rodante de arrastre, Caravana, Trailer

    @Column(length = 100, nullable = false)
    private String dimensiones; // ej: "7m x 2.5m"

    // ============================================================================
    // RELACIONES EN CAPAS (POO / HIBERNATE)
    // ============================================================================

    /**
     * Relación: Un vehículo pertenece a un único Socio (Many-to-One).
     * Mapea la clave foránea 'socio_id' que apunta a la tabla 'socios'.
     * Se usa FetchType.LAZY por buenas prácticas de optimización de memoria.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    /**
     * Relación 1:1 Opcional: Un vehículo ocupa un único Garage.
     * El 'mappedBy = "vehiculo"' le indica a Hibernate que el dueño físico
     * de la relación (el que guarda la columna de la FK en MySQL) es la tabla 'garages'.
     */
    @OneToOne(mappedBy = "vehiculo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Garage garage;
}

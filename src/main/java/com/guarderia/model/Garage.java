package com.guarderia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "garages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Garage {

    @Id
    @Column(name = "numero_garage")
    private Integer numeroGarage;

    @Column(name = "lectura_contador_luz", nullable = false)
    private Double lecturaContadorLuz = 0.0;

    @Column(name = "tiene_mantenimiento", nullable = false)
    private Boolean tieneMantenimiento = false;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    // ============================================================================
    // REQUISITO EXPLÍCITO DE LA CONSIGNA
    // ============================================================================
    @Column(name = "fecha_asignacion_vehiculo")
    private LocalDate fechaAsignacionVehiculo; // Fecha en que el vehículo entra al garage

    // ============================================================================
    // RELACIONES DE CONTEXTO
    // ============================================================================

    // Relación: Muchos garages pertenecen a una Zona
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    // Relación Opcional: Muchos garages pueden ser comprados por un Socio (puede ser NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socio;// Propietario del garage

    // Relación 1:1 Opcional: Un garage puede albergar un vehículo (puede ser NULL)
    // unique = true asegura a nivel de base de datos que un vehículo no se duplique en dos garages
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_matricula", unique = true)
    private Vehiculo vehiculo;// Vehículo estacionado
}


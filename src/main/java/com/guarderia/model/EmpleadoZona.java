package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

// 1. Clave primaria compuesta
//@Embeddable
//@Data
//public class EmpleadoZonaId implements Serializable {
//    private Long empleadoId;
//   private String zonaId;
//}

// 2. Entidad intermedia con el atributo propio
@Entity
@Table(name = "empleados_zonas")
@Data
public class EmpleadoZona {

    @EmbeddedId
    private EmpleadoZonaId id = new EmpleadoZonaId();

    @ManyToOne
    @MapsId("empleadoId")
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @MapsId("zonaId")
    @JoinColumn(name = "zona_id")
    private Zona zona;

    @Column(name = "cantidad_vehiculos_a_cargo")
    private Integer cantidadVehiculosACargo;
}

package com.guarderia.repositories;

import com.guarderia.model.entities.AsignacionEmpleadoZona;
import com.guarderia.model.entities.AsignacionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignacionRepository extends JpaRepository<AsignacionEmpleadoZona, AsignacionId> {
    // Permite al Empleado consultar únicamente sus zonas y vehículos asignados
    List<AsignacionEmpleadoZona> findByEmpleadoId(Long empleadoId);
}

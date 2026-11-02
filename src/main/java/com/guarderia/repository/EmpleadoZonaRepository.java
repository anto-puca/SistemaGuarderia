package com.guarderia.repository;

import com.guarderia.model.EmpleadoZona;
import com.guarderia.model.EmpleadoZonaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoZonaRepository extends JpaRepository<EmpleadoZona, EmpleadoZonaId> {
    // Permite gestionar las asignaciones directamente
}


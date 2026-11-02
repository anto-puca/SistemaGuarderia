package com.guarderia.repository;

import com.guarderia.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Hereda los métodos CRUD básicos de JpaRepository
}

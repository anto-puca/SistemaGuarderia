package com.guarderia.repository;

import com.guarderia.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    // JpaRepository ya incluye por defecto: save(), findAll(), findById() y existsById()
}

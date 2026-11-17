package com.guarderia.repositories;

import com.guarderia.model.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    List<Vehiculo> findBySocioId(Long socioId); // Para que el Socio consulte solo sus vehículos
}


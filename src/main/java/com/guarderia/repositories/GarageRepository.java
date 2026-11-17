package com.guarderia.repositories;

import com.guarderia.model.entities.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Integer> {

    // Lista los garages libres
    List<Garage> findByVehiculoIsNull();

    // Si necesitás buscar garages comprados por un socio, debe decir 'Propietario'
    List<Garage> findByPropietarioId(Long socioId);
}

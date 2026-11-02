package com.guarderia.repository;

import com.guarderia.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio de Persistencia para la entidad de negocio Garage.
 * Mapea directamente contra la tabla "garages" en MySQL mediante Hibernate.
 * Utiliza Integer como tipo de dato identificador único de la Clave Primaria (numero_garage).
 */
@Repository
public interface GarageRepository extends JpaRepository<Garage, Integer> {

    // ============================================================================
    // GESTIÓN COMERCIAL: QUERIES DE FILTRADO DE DISPONIBILIDAD (CONSIGNA ORIGINAL)
    // ============================================================================

    /**
     * Recupera todos los garages de la guardería que no han sido vendidos.
     * Spring Data JPA deriva automáticamente la consulta SQL nativa:
     * "SELECT * FROM garages WHERE socio_id IS NULL"
     *
     * @return List de Garages disponibles para asignación de propiedad (venta).
     */
    List<Garage> findBySocioIsNull();

    /**
     * Recupera todas las celdas físicas donde actualmente no hay ninguna unidad amarrada.
     * Cumple con la regla de negocio que permite guardar vehículos sin adquirir la propiedad.
     * Spring Data JPA deriva automáticamente la consulta SQL nativa:
     * "SELECT * FROM garages WHERE vehiculo_matricula IS NULL"
     *
     * @return List de Garages disponibles para ocupación física (estacionamiento).
     */
    List<Garage> findByVehiculoIsNull();
}

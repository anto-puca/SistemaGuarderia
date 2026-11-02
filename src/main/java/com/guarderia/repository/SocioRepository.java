package com.guarderia.repository;

import com.guarderia.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repositorio de Persistencia para la entidad Socio.
 * Extiende JpaRepository pasándole la clase de la entidad y el tipo de dato de su ID (Long).
 * Hibernate interpreta estas firmas para generar las consultas SQL en MySQL de manera automática.
 */

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    // Hereda automáticamente métodos como save(), findById(), deleteById()

    // ============================================================================
    // REQUISITO EXPLÍCITO DE LA CONSIGNA: "Listar a los socios por antigüedad"
    // ============================================================================

    /**
     * Busca todos los socios de la guardería y los ordena automáticamente
     * por su fecha de ingreso de forma ascendente (el más antiguo primero).
     * Spring Data JPA analiza el nombre del método (Query Method) y genera el SQL:
     * "SELECT * FROM socios s JOIN usuarios u ON s.usuario_id = u.id ORDER BY s.fecha_ingreso ASC"
     */
    List<Socio> findAllByOrderByFechaIngresoAsc();

    // ============================================================================
    // MÉTODOS COMPLEMENTARIOS ÚTILES PARA VALIDACIONES (SOLID / Buenas Prácticas)
    // ============================================================================

    /**
     * Verifica si ya existe un socio registrado con un DNI específico.
     * Útil en el Servicio para lanzar una excepción personalizada antes de guardar un duplicado.
     */
    boolean existsByDni(String dni);
}
package com.guarderia.repositories;

import com.guarderia.model.entities.Socio;
import com.guarderia.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    // Consulta específica para listar socios ordenados por antigüedad
    @Query("SELECT s FROM Socio s ORDER BY s.fechaIngreso ASC")
    List<Socio> findAllSociosByAntiguedad();

    // Agregar esta línea dentro de UsuarioRepository.java:
    @org.springframework.data.jpa.repository.Query("SELECT e FROM Empleado e")
    List<com.guarderia.model.entities.Empleado> findAllEmpleados();
}

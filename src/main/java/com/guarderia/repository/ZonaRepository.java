package com.guarderia.repository;

import com.guarderia.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, String> {
// Hereda de forma nativa los métodos CRUD: save(), findAll(), findById(), deleteById(), existsById()
}


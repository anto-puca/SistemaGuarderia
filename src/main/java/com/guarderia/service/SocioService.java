package com.guarderia.service;

import com.guarderia.model.Socio;
import com.guarderia.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocioService {

    private final SocioRepository socioRepository;
    // Inyección de dependencias por constructor (SOLID - Inversión de Dependencias)
    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }
    @Transactional(readOnly = true)
    public List<Socio> listarTodos() {
        return socioRepository.findAll();
    }

    /**
     * Guarda o actualiza un socio aplicando validaciones de negocio previas.
     * @throws IllegalArgumentException si el DNI ya se encuentra registrado por otro socio.
     */
    @Transactional
    @SuppressWarnings("null")
    public Socio guardar(Socio socio) {
        // VALIDACIÓN DE SEGURIDAD OPERATIVA (Impedir duplicación de documentos)
        // Solo validamos si es un alta nueva (id es nulo) para no trabar las modificaciones (updates)
        if (socio.getId() == null && socioRepository.existsByDni(socio.getDni())) {
            throw new IllegalArgumentException("Error operativo: Ya existe un socio registrado con el DNI " + socio.getDni());
        }

        return socioRepository.save(socio);
    }

    @Transactional(readOnly = true)
    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
    }

    /**
     * Elimina físicamente un socio y su correspondiente cascada en cascada en la BD.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!socioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El socio no existe.");
        }
        socioRepository.deleteById(id);
    }

    /**
     * REQUISITO CONSIGNA: Retorna la lista de socios ordenada por antigüedad.
     * Consume el Query Method especializado del repositorio.
     */
    @Transactional(readOnly = true)
    public List<Socio> listarPorAntiguedad() {
        return socioRepository.findAllByOrderByFechaIngresoAsc();
    }
}


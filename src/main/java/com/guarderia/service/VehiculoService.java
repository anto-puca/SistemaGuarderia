package com.guarderia.service;

import com.guarderia.model.Vehiculo;
import com.guarderia.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    @Transactional
    public void guardar(Vehiculo vehiculo, boolean esAltaNueva) {
        if (esAltaNueva && vehiculoRepository.existsById(vehiculo.getMatricula())) {
            throw new IllegalArgumentException("La matrícula '" + vehiculo.getMatricula() + "' ya se encuentra registrada en la guardería.");
        }
        vehiculoRepository.save(vehiculo);
    }

    @Transactional(readOnly = true)
    public Vehiculo buscarPorMatricula(String matricula) {
        return vehiculoRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún vehículo con la matrícula: " + matricula));
    }

    // ============================================================================
    // ADICIÓN REQUERIMIENTO COMPLETO: BAJA (DELETE)
    // ============================================================================
    @Transactional
    public void eliminar(String matricula) {
        if (!vehiculoRepository.existsById(matricula)) {
            throw new RuntimeException("Error operativo: El vehículo con matrícula " + matricula + " no existe.");
        }
        vehiculoRepository.deleteById(matricula);
    }
}

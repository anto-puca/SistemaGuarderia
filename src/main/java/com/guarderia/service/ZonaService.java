package com.guarderia.service;

import com.guarderia.model.Zona;
import com.guarderia.repository.ZonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;
    // Inyección por constructor (SOLID - Inversión de Dependencias)
    public ZonaService(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @Transactional(readOnly = true)
    public List<Zona> listarTodas() {
        return zonaRepository.findAll();
    }

    @Transactional
    public void guardar(Zona zona, boolean esAltaNueva) {
        // VALIDACIÓN DE SEGURIDAD OPERATIVA: Evitar zonas duplicadas (ej: Crear dos zonas "A")
        if (esAltaNueva && zonaRepository.existsById(zona.getLetra())) {
            throw new IllegalArgumentException("Error operativo: La Zona '" + zona.getLetra() + "' ya se encuentra registrada en el sistema.");
        }
        zonaRepository.save(zona);
    }

    @Transactional(readOnly = true)
    public Zona buscarPorLetra(String letra) {
        return zonaRepository.findById(letra)
                .orElseThrow(() -> new RuntimeException("No se encontró la zona con la letra: " + letra));
    }

    @Transactional
    public void eliminar(String letra) {
        if (!zonaRepository.existsById(letra)) {
            throw new RuntimeException("Error operativo: La zona con letra " + letra + " no existe.");
        }
        zonaRepository.deleteById(letra);
    }
}

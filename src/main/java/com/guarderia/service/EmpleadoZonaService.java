package com.guarderia.service;

import com.guarderia.model.EmpleadoZona;
import com.guarderia.model.EmpleadoZonaId;
import com.guarderia.repository.EmpleadoZonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmpleadoZonaService {

    private final EmpleadoZonaRepository empleadoZonaRepository;

    public EmpleadoZonaService(EmpleadoZonaRepository empleadoZonaRepository) {
        this.empleadoZonaRepository = empleadoZonaRepository;
    }

    public List<EmpleadoZona> listarTodas() {
        return empleadoZonaRepository.findAll();
    }

    @Transactional
    public void guardar(EmpleadoZona empleadoZona) {
        // Inyectamos manualmente los IDs en la clave compuesta antes de salvar
        EmpleadoZonaId id = new EmpleadoZonaId();
        id.setEmpleadoId(empleadoZona.getEmpleado().getId());
        id.setZonaId(empleadoZona.getZona().getLetra());
        empleadoZona.setId(id);

        empleadoZonaRepository.save(empleadoZona);
    }

    @Transactional
    public void eliminar(Long empleadoId, String zonaId) {
        EmpleadoZonaId id = new EmpleadoZonaId();
        id.setEmpleadoId(empleadoId);
        id.setZonaId(zonaId);
        empleadoZonaRepository.deleteById(id);
    }
}

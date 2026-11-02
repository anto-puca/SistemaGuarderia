package com.guarderia.service;

import com.guarderia.model.Empleado;
import com.guarderia.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    @Transactional
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}

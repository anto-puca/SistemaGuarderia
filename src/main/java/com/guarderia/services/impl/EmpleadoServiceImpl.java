package com.guarderia.services.impl;

import com.guarderia.model.dtos.AsignacionDTO;
import com.guarderia.model.dtos.EmpleadoCreateDTO;
import com.guarderia.model.dtos.EmpleadoDTO;
import com.guarderia.model.entities.*;
import com.guarderia.repositories.AsignacionRepository;
import com.guarderia.repositories.UsuarioRepository;
import com.guarderia.repositories.ZonaRepository;
import com.guarderia.services.interfaces.IEmpleadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    private final UsuarioRepository usuarioRepo;
    private final ZonaRepository zonaRepo;
    private final AsignacionRepository asignacionRepo;

    public EmpleadoServiceImpl(UsuarioRepository usuarioRepo, ZonaRepository zonaRepo, AsignacionRepository asignacionRepo) {
        this.usuarioRepo = usuarioRepo;
        this.zonaRepo = zonaRepo;
        this.asignacionRepo = asignacionRepo;
    }

    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        return usuarioRepo.findAllEmpleados().stream()
                .map(EmpleadoDTO::new)
                .toList();
    }

    @Override
    public EmpleadoDTO registrarEmpleado(EmpleadoCreateDTO dto) {
        Empleado emp = new Empleado(
                dto.nombre(),
                dto.direccion(),
                dto.telefono(),
                dto.email(),
                dto.password(),
                dto.codigo(),
                dto.especialidad()
        );
        return new EmpleadoDTO(usuarioRepo.save(emp));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        usuarioRepo.deleteById(id);
    }

    @Override
    @Transactional
    public AsignacionDTO asignarEmpleadoAZona(AsignacionDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.empleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        if (!(usuario instanceof Empleado empleado)) {
            throw new RuntimeException("El usuario seleccionado no es un Empleado");
        }

       Zona zona = zonaRepo.findById(dto.zonaLetra())


                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        // Creamos la entidad intermedia con la clave compuesta (empleadoId + zonaLetra)
        // Si ya existía la combinación, save() actualiza la cantidad de vehículos a cargo
        AsignacionEmpleadoZona asignacion = new AsignacionEmpleadoZona(
                empleado,
                zona,
                dto.cantidadVehiculosACargo()
        );

        return new AsignacionDTO(asignacionRepo.save(asignacion));
    }

    @Override
    public List<AsignacionDTO> listarAsignacionesPorEmpleado(Long empleadoId) {
        return asignacionRepo.findByEmpleadoId(empleadoId).stream()
                .map(AsignacionDTO::new)
                .toList();
    }

    @Override
    public List<AsignacionDTO> listarTodasLasAsignaciones() {
        return asignacionRepo.findAll().stream()
                .map(AsignacionDTO::new)
                .toList();
    }

    @Override
    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoCreateDTO dto) {
        Empleado emp = (Empleado) usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        emp.setNombre(dto.nombre());
        emp.setDireccion(dto.direccion());
        emp.setTelefono(dto.telefono());
        emp.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isBlank()) {
            emp.setPassword(dto.password());
        }
        emp.setCodigo(dto.codigo());
        emp.setEspecialidad(dto.especialidad());
        return new EmpleadoDTO(usuarioRepo.save(emp));
    }
}

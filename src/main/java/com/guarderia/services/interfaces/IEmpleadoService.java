package com.guarderia.services.interfaces;

import com.guarderia.model.dtos.AsignacionDTO;
import com.guarderia.model.dtos.EmpleadoCreateDTO;
import com.guarderia.model.dtos.EmpleadoDTO;
import java.util.List;

public interface IEmpleadoService {
    List<EmpleadoDTO> listarEmpleados();
    EmpleadoDTO registrarEmpleado(EmpleadoCreateDTO dto);
    void eliminarEmpleado(Long id);

    // Gestión de relación N:M con clave compuesta
    AsignacionDTO asignarEmpleadoAZona(AsignacionDTO dto);
    List<AsignacionDTO> listarAsignacionesPorEmpleado(Long empleadoId);
    List<AsignacionDTO> listarTodasLasAsignaciones();
}

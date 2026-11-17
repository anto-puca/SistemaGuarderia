package com.guarderia.controllers;

import com.guarderia.model.dtos.AsignacionDTO;
import com.guarderia.model.dtos.EmpleadoCreateDTO;
import com.guarderia.model.dtos.EmpleadoDTO;
import com.guarderia.services.interfaces.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final IEmpleadoService empleadoService;

    public EmpleadoController(IEmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.registrarEmpleado(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS PARA ASIGNACIÓN A ZONAS (N:M) ---

    @PostMapping("/asignar-zona")
    public ResponseEntity<?> asignarAZona(@Valid @RequestBody AsignacionDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(empleadoService.asignarEmpleadoAZona(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/asignaciones")
    public ResponseEntity<List<AsignacionDTO>> listarTodasAsignaciones() {
        return ResponseEntity.ok(empleadoService.listarTodasLasAsignaciones());
    }

    // Endpoint exclusivo para que el Empleado consulte sus propias zonas
    @GetMapping("/{empleadoId}/asignaciones")
    public ResponseEntity<List<AsignacionDTO>> listarMisAsignaciones(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(empleadoService.listarAsignacionesPorEmpleado(empleadoId));
    }
}

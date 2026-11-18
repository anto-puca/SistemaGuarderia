package com.guarderia.controllers;

import com.guarderia.model.dtos.*;
import com.guarderia.services.interfaces.IGestionGuarderiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InstalacionesController {

    private final IGestionGuarderiaService gestionService;

    public InstalacionesController(IGestionGuarderiaService gestionService) {
        this.gestionService = gestionService;
    }

    // --- ENDPOINTS ZONAS ---
    @GetMapping("/zonas")
    public ResponseEntity<List<ZonaDTO>> listarZonas() {
        return ResponseEntity.ok(gestionService.listarZonas());
    }

    @PostMapping("/zonas")
    public ResponseEntity<ZonaDTO> crearZona(@Valid @RequestBody ZonaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gestionService.guardarZona(dto));
    }

    @DeleteMapping("/zonas/{letra}")
    public ResponseEntity<Void> eliminarZona(@PathVariable String letra) {
        gestionService.eliminarZona(letra);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS GARAGES Y COMPRA ---
    @GetMapping("/garages")
    public ResponseEntity<List<GarageDTO>> listarGarages() {
        return ResponseEntity.ok(gestionService.listarGarages());
    }

    @PostMapping("/garages")
    public ResponseEntity<GarageDTO> crearGarage(@Valid @RequestBody GarageDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gestionService.guardarGarage(dto));
    }

    @PutMapping("/garages/{numeroGarage}/comprar")
    public ResponseEntity<?> comprarGarage(@PathVariable Integer numeroGarage,
                                           @RequestParam Long socioId,
                                           @RequestParam(required = false) LocalDate fechaCompra) {
        try {
            return ResponseEntity.ok(gestionService.registrarCompraGarage(numeroGarage, socioId, fechaCompra));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- ENDPOINTS VEHÍCULOS Y ASIGNACIÓN ---
    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoDTO>> listarVehiculos() {
        return ResponseEntity.ok(gestionService.listarVehiculos());
    }

    @GetMapping("/vehiculos/socio/{socioId}")
    public ResponseEntity<List<VehiculoDTO>> listarVehiculosDeSocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(gestionService.listarVehiculosPorSocio(socioId));
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<?> registrarVehiculo(@Valid @RequestBody VehiculoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(gestionService.registrarVehiculoYAsignarGarage(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    ////
    @PutMapping("/vehiculos/{matricula}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable String matricula, @Valid @RequestBody VehiculoDTO dto) {
        try {
            return ResponseEntity.ok(gestionService.actualizarVehiculo(matricula, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @PutMapping("/zonas/{letra}")
    public ResponseEntity<ZonaDTO> actualizarZona(@PathVariable String letra, @Valid @RequestBody ZonaDTO dto) {
        return ResponseEntity.ok(gestionService.guardarZona(dto));
    }

    @PutMapping("/garages/{numeroGarage}")
    public ResponseEntity<GarageDTO> actualizarGarage(@PathVariable Integer numeroGarage, @Valid @RequestBody GarageDTO dto) {
        return ResponseEntity.ok(gestionService.guardarGarage(dto));
    }

    @DeleteMapping("/garages/{numeroGarage}")
    public ResponseEntity<Void> eliminarGarage(@PathVariable Integer numeroGarage) {
        // Asegurate de tener garageRepo.deleteById(numeroGarage) en tu servicio
        gestionService.eliminarGarage(numeroGarage);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/vehiculos/{matricula}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String matricula) {
        // Asegurate de liberar el garage y borrar el vehículo en tu servicio
        gestionService.eliminarVehiculo(matricula);
        return ResponseEntity.noContent().build();
    }

}


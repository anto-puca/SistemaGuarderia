package com.guarderia.controllers;

import com.guarderia.model.dtos.*;
import com.guarderia.services.interfaces.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        try {
            return ResponseEntity.ok(usuarioService.autenticar(loginDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/socios/antiguedad")
    public ResponseEntity<List<SocioDTO>> listarSociosAntiguedad() {
        return ResponseEntity.ok(usuarioService.listarSociosPorAntiguedad());
    }

    @PostMapping("/socios")
    public ResponseEntity<SocioDTO> crearSocio(@Valid @RequestBody SocioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarSocio(dto));
    }

    @DeleteMapping("/socios/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        usuarioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    }
}

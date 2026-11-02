package com.guarderia.controller;

import com.guarderia.model.EmpleadoZona;
import com.guarderia.repository.ZonaRepository;
import com.guarderia.service.EmpleadoZonaService;
import com.guarderia.service.EmpleadoService;
import com.guarderia.service.ZonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asignaciones")
public class EmpleadoZonaController {

    private final EmpleadoZonaService asignacionService;
    private final EmpleadoService empleadoService;
    private final ZonaService zonaService; // <-- Corregido

    public EmpleadoZonaController(EmpleadoZonaService asignacionService, EmpleadoService empleadoService, ZonaService zonaService) {
        this.asignacionService = asignacionService;
        this.empleadoService = empleadoService;
        this.zonaService = zonaService;
    }

    @GetMapping
    public String listarAsignaciones(Model model) {
        model.addAttribute("asignaciones", asignacionService.listarTodas());
        return "asignaciones/lista";
    }



    @GetMapping("/nueva")
    public String mostrarFormularioAsignacion(Model model) {
        model.addAttribute("asignacion", new EmpleadoZona());
        model.addAttribute("empleados", empleadoService.listarTodos());
        model.addAttribute("zonas", zonaService.listarTodas());
        return "asignaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAsignacion(@ModelAttribute("asignacion") EmpleadoZona asignacion) {
        asignacionService.guardar(asignacion);
        return "redirect:/asignaciones";
    }

    @GetMapping("/eliminar")
    public String eliminarAsignacion(@RequestParam Long empleadoId, @RequestParam String zonaId) {
        asignacionService.eliminar(empleadoId, zonaId);
        return "redirect:/asignaciones";
    }
}


package com.guarderia.controller;

import com.guarderia.model.Vehiculo;
import com.guarderia.service.VehiculoService;
import com.guarderia.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final SocioService socioService;

    public VehiculoController(VehiculoService vehiculoService, SocioService socioService) {
        this.vehiculoService = vehiculoService;
        this.socioService = socioService;
    }

    @GetMapping
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.listarTodos());
        return "vehiculos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
        if (!model.containsAttribute("vehiculo")) {
            model.addAttribute("vehiculo", new Vehiculo());
        }
        model.addAttribute("socios", socioService.listarTodos());
        return "vehiculos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String matricula, Model model) {
        model.addAttribute("vehiculo", vehiculoService.buscarPorMatricula(matricula));
        model.addAttribute("socios", socioService.listarTodos());
        return "vehiculos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo,
                                  @RequestParam(value = "isAlta", defaultValue = "true") boolean isAlta,
                                  RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.guardar(vehiculo, isAlta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Vehículo procesado con éxito.");
            return "redirect:/vehiculos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            redirectAttributes.addFlashAttribute("vehiculo", vehiculo);
            return "redirect:/vehiculos/nuevo";
        }
    }

    // ============================================================================
    // ADICIÓN REQUERIMIENTO COMPLETO: MAPEO DE BAJA (DELETE HTTP)
    // ============================================================================
    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable("id") String matricula, RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.eliminar(matricula);
            redirectAttributes.addFlashAttribute("mensajeExito", "Vehículo removido del padrón correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar el vehículo: " + e.getMessage());
        }
        return "redirect:/vehiculos";
    }
}

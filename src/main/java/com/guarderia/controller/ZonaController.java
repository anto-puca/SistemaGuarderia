package com.guarderia.controller;

import com.guarderia.model.Zona;
import com.guarderia.service.ZonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    // L - LISTAR
    @GetMapping
    public String listarZonas(Model model) {
        model.addAttribute("zonas", zonaService.listarTodas());
        return "zonas/lista";
    }

    // A - ALTA: Formulario vacío
    @GetMapping("/nueva")
    public String mostrarFormularioAlta(Model model) {
        if (!model.containsAttribute("zona")) {
            model.addAttribute("zona", new Zona());
        }
        return "zonas/formulario";
    }

    // M - MODIFICACIÓN: Formulario precargado
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String letra, Model model) {
        model.addAttribute("zona", zonaService.buscarPorLetra(letra));
        return "zonas/formulario";
    }

    // PROCESAR GUARDADO (Alta y Edición unificadas)
    @PostMapping("/guardar")
    public String guardarZona(@ModelAttribute("zona") Zona zona,
                              @RequestParam(value = "isAlta", defaultValue = "true") boolean isAlta,
                              RedirectAttributes redirectAttributes) {
        try {
            zonaService.guardar(zona, isAlta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Zona guardada de manera exitosa.");
            return "redirect:/zonas";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            redirectAttributes.addFlashAttribute("zona", zona);
            return "redirect:/zonas/nueva";
        }
    }

    // B - BAJA (DELETE HTTP)
    @GetMapping("/eliminar/{id}")
    public String eliminarZona(@PathVariable("id") String letra, RedirectAttributes redirectAttributes) {
        try {
            zonaService.eliminar(letra);
            redirectAttributes.addFlashAttribute("mensajeExito", "La zona fue eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar la zona: " + e.getMessage());
        }
        return "redirect:/zonas";
    }
}

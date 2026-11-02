package com.guarderia.controller;

import com.guarderia.model.Garage;
import com.guarderia.service.GarageService;
import com.guarderia.service.SocioService;
import com.guarderia.service.VehiculoService;
import com.guarderia.service.ZonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/garages")
public class GarageController {

    private final GarageService garageService;
    private final SocioService socioService;
    private final VehiculoService vehiculoService;
    private final ZonaService zonaService;

    // Inyección de dependencias por constructor (SOLID - Arquitectura en Capas)
    public GarageController(GarageService garageService, SocioService socioService,
                            VehiculoService vehiculoService, ZonaService zonaService) {
        this.garageService = garageService;
        this.socioService = socioService;
        this.vehiculoService = vehiculoService;
        this.zonaService = zonaService;
    }

    // ============================================================================
    // A B M   F U N C I O N A L   (ADMINISTRACIÓN DEL INVENTARIO)
    // ============================================================================

    // 1. LISTAR (Read)
    @GetMapping
    public String listarGarages(Model model) {
        model.addAttribute("garages", garageService.listarTodos());
        return "garages/lista";
    }

    // 2. ALTA: Mostrar Formulario (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
        if (!model.containsAttribute("garage")) {
            model.addAttribute("garage", new Garage());
        }
        model.addAttribute("zonas", zonaService.listarTodas()); // Alimenta el select de Zonas (A, B, C, D)
        return "garages/formulario_inventario";
    }

    // 3. PROCESAR GUARDADO (Aplica para creación y modificaciones estructurales)
    @PostMapping("/guardar")
    public String guardarGarage(@ModelAttribute("garage") Garage garage,
                                @RequestParam(value = "isAlta", defaultValue = "true") boolean isAlta,
                                RedirectAttributes redirectAttributes) {
        try {
            if (isAlta) {
                garageService.guardarNuevoGarage(garage);
            } else {
                // Si es edición, se usa el save directo de persistencia
                Garage existente = garageService.buscarPorId(garage.getNumeroGarage());
                existente.setLecturaContadorLuz(garage.getLecturaContadorLuz());
                existente.setZona(garage.getZona());
                garageService.guardarNuevoGarage(existente); // Actualiza
            }
            redirectAttributes.addFlashAttribute("mensajeExito", "Estructura de Garage procesada correctamente.");
            return "redirect:/garages";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            redirectAttributes.addFlashAttribute("garage", garage);
            return "redirect:/garages/nuevo";
        }
    }

    // 4. MODIFICACIÓN: Mostrar Formulario Precargado (Update)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer numeroGarage, Model model) {
        model.addAttribute("garage", garageService.buscarPorId(numeroGarage));
        model.addAttribute("zonas", zonaService.listarTodas());
        return "garages/formulario_inventario";
    }

    // 5. BAJA: Eliminar de la base de datos (Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarGarageFisico(@PathVariable("id") Integer numeroGarage, RedirectAttributes redirectAttributes) {
        try {
            garageService.eliminarGarageFisico(numeroGarage);
            redirectAttributes.addFlashAttribute("mensajeExito", "El garage Nº " + numeroGarage + " fue removido del inventario físico.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar: " + e.getMessage());
        }
        return "redirect:/garages";
    }

    // ============================================================================
    // GESTIÓN COMERCIAL Y OPERATIVA (LOGICA DE NEGOCIO DE LA CONSIGNA)
    // ============================================================================

    // COMPRA / PROPIEDAD
    @GetMapping("/comprar/{numeroGarage}")
    public String mostrarFormularioCompra(@PathVariable Integer numeroGarage, Model model) {
        model.addAttribute("garage", garageService.buscarPorId(numeroGarage));
        model.addAttribute("socios", socioService.listarTodos());
        return "garages/formulario_compra";
    }

    @PostMapping("/guardar-compra")
    public String guardarCompra(@RequestParam Integer numeroGarage, @RequestParam Long socioId,
                                @RequestParam(value = "tieneMantenimiento", defaultValue = "false") Boolean tieneMantenimiento,
                                RedirectAttributes redirectAttributes) {
        try {
            garageService.registrarCompraDeGarage(numeroGarage, socioId, tieneMantenimiento);
            redirectAttributes.addFlashAttribute("mensajeExito", "Propiedad del Garage Nº " + numeroGarage + " asignada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/garages";
    }

    // ESTACIONAMIENTO (ACTIVIDAD PRINCIPAL)
    @GetMapping("/estacionar/{numeroGarage}")
    public String mostrarFormularioEstacionar(@PathVariable Integer numeroGarage, Model model) {
        model.addAttribute("garage", garageService.buscarPorId(numeroGarage));
        model.addAttribute("vehiculos", vehiculoService.listarTodos());
        return "garages/formulario_estacionar";
    }

    @PostMapping("/guardar-estacionamiento")
    public String guardarEstacionamiento(@RequestParam Integer numeroGarage, @RequestParam(required = false) String matriculaVehiculo,
                                         RedirectAttributes redirectAttributes) {
        try {
            garageService.guardarVehiculoEnGarage(numeroGarage, matriculaVehiculo);
            redirectAttributes.addFlashAttribute("mensajeExito", "Ocupación física del Garage Nº " + numeroGarage + " actualizada de forma segura.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/garages";
    }

    // LIBERACIÓN RÁPIDA
    @GetMapping("/liberar/{numeroGarage}")
    public String liberarGarage(@PathVariable Integer numeroGarage, RedirectAttributes redirectAttributes) {
        try {
            garageService.liberarGarageCompleto(numeroGarage);
            redirectAttributes.addFlashAttribute("mensajeExito", "El garage Nº " + numeroGarage + " fue vaciado y desvinculado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/garages";
    }
}

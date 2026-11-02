package com.guarderia.controller;

import com.guarderia.service.SocioService;
import com.guarderia.service.EmpleadoService;
import com.guarderia.service.VehiculoService;
import com.guarderia.service.GarageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador raíz del sistema.
 * Orquesta la página de inicio (Dashboard) inyectando de forma transversal
 * los contadores de todas las entidades operativas del negocio.
 */
@Controller
public class DashboardController {

    private final SocioService socioService;
    private final EmpleadoService empleadoService;
    private final VehiculoService vehiculoService;
    private final GarageService garageService;

    // Inyección de dependencias exclusiva por constructor (SOLID - Inversión de Dependencias)
    public DashboardController(SocioService socioService, EmpleadoService empleadoService,
                               VehiculoService vehiculoService, GarageService garageService) {
        this.socioService = socioService;
        this.empleadoService = empleadoService;
        this.vehiculoService = vehiculoService;
        this.garageService = garageService;
    }

    /**
     * Escucha las peticiones en la raíz del servidor.
     * Alimenta los "badges" dinámicos del menú con los tamaños de las listas del Service.
     */
    @GetMapping("/")
    public String index(Model model) {
        // Consultamos a la capa de Servicios y subimos las métricas al modelo de Thymeleaf
        model.addAttribute("totalSocios", socioService.listarTodos().size());
        model.addAttribute("totalEmpleados", empleadoService.listarTodos().size());
        model.addAttribute("totalVehiculos", vehiculoService.listarTodos().size());
        model.addAttribute("totalGarages", garageService.listarTodos().size());

        return "dashboard"; // Busca y renderiza el archivo src/main/resources/templates/dashboard.html
    }
}

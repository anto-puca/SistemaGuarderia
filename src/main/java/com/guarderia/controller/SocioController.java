package com.guarderia.controller;

import com.guarderia.model.Socio;
import com.guarderia.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/socios")
public class SocioController {

    private final SocioService socioService;

    // Inyección de dependencias por constructor (Buenas prácticas / SOLID)
    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    // ============================================================================
    // REPORTE EXCLUSIVO DEL ADMINISTRADOR (CONSIGNA)
    // ============================================================================
    @GetMapping("/antiguedad")
    public String listarPorAntiguedad(Model model) {
        model.addAttribute("socios", socioService.listarPorAntiguedad());
        return "socios/lista_antiguedad";
    }


    // L - LISTAR (Read)
    @GetMapping
    public String listarSocios(Model model) {
        model.addAttribute("socios", socioService.listarTodos());
        return "socios/lista"; // Redirecciona a src/main/resources/templates/socios/lista.html
    }

    // A - ALTA: Mostrar formulario (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
        Socio socio = new Socio();
        socio.setTipoUsuario("SOCIO"); // Setear por defecto el tipo de herencia
        model.addAttribute("socio", socio);
        return "socios/formulario";
    }

    // M - MODIFICACIÓN: Mostrar formulario con datos cargados (Update)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("socio", socioService.buscarPorId(id));
        return "socios/formulario";
    }

    // GUARDAR (Procesa tanto Alta como Modificación)
    // ============================================================================
    // PROCESAR GUARDADO CON CONTROL DE EXCEPCIONES OPERATIVAS
    // ============================================================================
    @PostMapping("/guardar")
    public String guardarSocio(@ModelAttribute("socio") Socio socio, RedirectAttributes redirectAttributes) {
        try {
            socioService.guardar(socio);
            // Si sale bien, mandamos un mensaje de éxito verde a la lista
            redirectAttributes.addFlashAttribute("mensajeExito", "Socio procesado correctamente.");
            return "redirect:/socios";
        } catch (IllegalArgumentException e) {
            // Si el servicio detecta DNI duplicado, atrapamos el error aquí
            // Enviamos el mensaje de error y los datos del formulario de vuelta para no perder lo escrito
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            redirectAttributes.addFlashAttribute("socio", socio);

            // Redirigimos de vuelta al formulario de alta de manera segura
            return "redirect:/socios/nuevo";
        }
    }


    // B - BAJA: Eliminar registro (Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            socioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Socio eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar el socio.");
        }
        return "redirect:/socios";
    }
}
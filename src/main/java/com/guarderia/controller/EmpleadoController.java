package com.guarderia.controller;

import com.guarderia.model.Empleado;
import com.guarderia.service.EmpleadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // LISTAR
    @GetMapping
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoService.listarTodos());
        return "empleados/lista";
    }

    // FORMULARIO ALTA
    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
        Empleado empleado = new Empleado();
        empleado.setTipoUsuario("EMPLEADO"); // Setear el discriminador de la herencia
        model.addAttribute("empleado", empleado);
        return "empleados/formulario";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("empleado", empleadoService.buscarPorId(id));
        return "empleados/formulario";
    }

    // PROCESAR GUARDADO
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return "redirect:/empleados";
    }
}


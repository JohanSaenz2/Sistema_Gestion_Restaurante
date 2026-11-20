package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.service.ComandaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BarViewController {

    private final ComandaService comandaService;

    public BarViewController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

   
    @GetMapping("/bar")
    public String bar(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        model.addAttribute("empleado", empleado);
        model.addAttribute("comandas",
            comandaService.activasPorEstacion(com.restaurante.Sistema.Enum.Estacion.bar));
        return "bar";
    }
}
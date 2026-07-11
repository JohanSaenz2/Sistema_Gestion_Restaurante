package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.service.ComandaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CocinaViewController {

    private final ComandaService comandaService;

    public CocinaViewController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @GetMapping("/cocina")
    public String cocina(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        model.addAttribute("empleado", empleado);
        // Solo comandas pendientes y en preparación
        model.addAttribute("pendientes",
            comandaService.porEstado(EstadoComanda.pendiente));
        model.addAttribute("enPreparacion",
            comandaService.porEstado(EstadoComanda.en_preparacion));
        return "cocina";
    }
}
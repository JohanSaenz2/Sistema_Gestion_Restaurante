// controller/CajaViewController.java
package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.service.ClienteService;
import com.restaurante.Sistema.service.ComandaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CajaViewController {

    private final ComandaService   comandaService;
    private final ClienteService   clienteService;

    public CajaViewController(ComandaService comandaService,
                               ClienteService clienteService) {
        this.comandaService = comandaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/caja")
    public String caja(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        model.addAttribute("empleado",     empleado);

        // Comandas listas para entregar
        model.addAttribute("listas",
            comandaService.porEstado(EstadoComanda.pendiente));

        // Comandas ya entregadas pendientes de cobro
        model.addAttribute("entregadas",
            comandaService.porEstado(EstadoComanda.entregada));

        model.addAttribute("clientes",
            clienteService.listarTodos());
        model.addAttribute("tiposComprobante",
            TipoComprobante.values());
        return "caja";
    }
}
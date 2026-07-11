package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.service.MesaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MesaViewController {

    private final MesaService mesaService;

    public MesaViewController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping("/mesas")
    public String mesas(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        model.addAttribute("empleado", empleado);
        model.addAttribute("mesas", mesaService.listarTodas());
        return "mesas";
    }
}

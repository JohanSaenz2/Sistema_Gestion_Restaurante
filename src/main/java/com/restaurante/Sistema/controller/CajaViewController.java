package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.service.ClienteService;
import com.restaurante.Sistema.service.ComandaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CajaViewController {

	private final ComandaService comandaService;
	private final ClienteService clienteService;
	private final ComprobanteRepository comprobanteRepo;

	public CajaViewController(ComandaService comandaService, ClienteService clienteService,
			ComprobanteRepository comprobanteRepo) {
		super();
		this.comandaService = comandaService;
		this.clienteService = clienteService;
		this.comprobanteRepo = comprobanteRepo;
	}

	@GetMapping("/caja")
	public String caja(Model model, HttpSession session) {
		Empleado empleado = (Empleado) session.getAttribute("empleado");
		model.addAttribute("empleado", empleado);

		model.addAttribute("listas", comandaService.porEstado(EstadoComanda.pendiente));

		model.addAttribute("entregadas", comandaService.porEstado(EstadoComanda.entregada));

		model.addAttribute("clientes", clienteService.listarTodos());
		model.addAttribute("tiposComprobante", TipoComprobante.values());
		model.addAttribute("comprobantes", comprobanteRepo.findAll());
		return "caja";
	}
	
	@GetMapping("/caja/nuevo-cliente")
	public String nuevoCliente(Model model, HttpSession session) {
	    model.addAttribute("empleado", session.getAttribute("empleado"));
	    return "nuevo-cliente";
	}
}
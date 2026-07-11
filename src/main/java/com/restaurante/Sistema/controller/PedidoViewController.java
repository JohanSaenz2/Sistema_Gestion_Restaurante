package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.model.Mesa;
import com.restaurante.Sistema.service.AdicionalesService;
import com.restaurante.Sistema.service.MesaService;
import com.restaurante.Sistema.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PedidoViewController {

	private final MesaService mesaService;
	private final ProductoService productoService;
	private final AdicionalesService adicionalesService;

	public PedidoViewController(MesaService mesaService, ProductoService productoService,
			AdicionalesService adicionalesService) {
		this.mesaService = mesaService;
		this.productoService = productoService;
		this.adicionalesService = adicionalesService;
	}

	@GetMapping("/pedido/{mesaId}")
	public String pedido(@PathVariable Long mesaId, Model model, HttpSession session) {

		Empleado empleado = (Empleado) session.getAttribute("empleado");
		Mesa mesa = mesaService.buscarPorId(mesaId);

		if (mesa == null)
			return "redirect:/mesas";

		model.addAttribute("empleado", empleado);
		model.addAttribute("mesa", mesa);
		model.addAttribute("productos", productoService.listarTodos());
		model.addAttribute("adicionales", adicionalesService.listarTodos());

		return "pedido";
	}
}
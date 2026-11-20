package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.service.ComandaService;
import com.restaurante.Sistema.service.EmpleadoService;
import com.restaurante.Sistema.service.MesaService;
import com.restaurante.Sistema.service.ProductoService;
import com.restaurante.Sistema.service.AdicionalesService;
import com.restaurante.Sistema.service.ClienteService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminViewController {

	private final ComandaService comandaService;
	private final EmpleadoService empleadoService;
	private final MesaService mesaService;
	private final ProductoService productoService;
	private final AdicionalesService adicionalesService;
	private final ComprobanteRepository comprobanteRepo;
	private final ClienteService clienteService;
	
	

	public AdminViewController(ComandaService comandaService, EmpleadoService empleadoService, MesaService mesaService,
			ProductoService productoService, AdicionalesService adicionalesService,
			ComprobanteRepository comprobanteRepo, ClienteService clienteService) {
		this.comandaService = comandaService;
		this.empleadoService = empleadoService;
		this.mesaService = mesaService;
		this.productoService = productoService;
		this.adicionalesService = adicionalesService;
		this.comprobanteRepo = comprobanteRepo;
		this.clienteService= clienteService;
	}

	@GetMapping("/admin")
	public String admin(Model model, HttpSession session) {
		Empleado empleado = (Empleado) session.getAttribute("empleado");
		model.addAttribute("empleado", empleado);
		model.addAttribute("comandas", comandaService.listarTodas());
		model.addAttribute("empleados", empleadoService.listarTodos());
		model.addAttribute("mesas", mesaService.listarTodas());
		model.addAttribute("productos", productoService.listarTodos());
		model.addAttribute("adicionales", adicionalesService.listarTodos());
		model.addAttribute("comprobantes", comprobanteRepo.findAll());
		 model.addAttribute("clientes", clienteService.listarTodos()); 
		return "admin";
	}

	@GetMapping("/admin/nuevo-cliente")
	public String nuevoCliente(Model model, HttpSession session) {
	    model.addAttribute("empleado", session.getAttribute("empleado"));
	    return "nuevo-cliente";
	}
	@GetMapping("/agregarProducto")
	public String nuevoProducto(Model model, HttpSession session) {
		model.addAttribute("empleado", session.getAttribute("empleado"));
		return "agregarProducto";
	}

	@GetMapping("/agregarEmpleado")
	public String nuevoEmpleado(Model model, HttpSession session) {
		model.addAttribute("empleado", session.getAttribute("empleado"));
		return "agregarEmpleado";
	}

	
	@GetMapping("/agregarMesas")
	public String nuevaMesa(Model model, HttpSession session) {
		model.addAttribute("empleado", session.getAttribute("empleado"));
		return "agregarMesas";
	}

	@GetMapping("/AgregarAdicionales")
	public String nuevoAdicional(Model model, HttpSession session) {
		model.addAttribute("empleado", session.getAttribute("empleado"));
		return "AgregarAdicionales";
	}

}
package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.Enum.RolEmpleados;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.repository.EmpleadoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

	private final EmpleadoRepository empleadoRepo;

	public AuthController(EmpleadoRepository empleadoRepo) {
		this.empleadoRepo = empleadoRepo;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String procesarLogin(@RequestParam String usuario, @RequestParam String password, HttpSession session,
			Model model) {

		Empleado empleado = empleadoRepo.findByUsuarioAndPassword(usuario, password);

		if (empleado == null) {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			return "login";
		}

		session.setAttribute("empleado", empleado);
		session.setAttribute("rol", empleado.getRol());

		return switch (empleado.getRol()) {
		case mesero -> "redirect:/mesas";
		case cocinero -> "redirect:/cocina";
		case bartender -> "redirect:/bar";
		case cajero -> "redirect:/caja";
		case administrador -> "redirect:/admin";
		default -> "redirect:/login";
		};
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}

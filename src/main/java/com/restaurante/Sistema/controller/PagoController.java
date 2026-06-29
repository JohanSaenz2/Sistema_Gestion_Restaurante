package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Pago;
import com.restaurante.Sistema.repository.PagoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

	private final PagoRepository repo;

	public PagoController(PagoRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Pago> listarTodos() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Pago buscarPorId(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}

	@PostMapping
	public Pago crear(@RequestBody Pago pago) {
		return repo.save(pago);
	}
}
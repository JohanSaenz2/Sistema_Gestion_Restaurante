package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

	private final ComprobanteRepository repo;

	public ComprobanteController(ComprobanteRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Comprobante> listarTodos() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Comprobante buscarPorId(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}

	@PostMapping
	public Comprobante crear(@RequestBody Comprobante comprobante) {
		return repo.save(comprobante);
	}
}
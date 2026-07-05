package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.dto.ComprobanteResponse;
import com.restaurante.Sistema.dto.GenerarComprobanteRequest;
import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.service.ComprobanteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

	private final ComprobanteRepository repo;
	private final ComprobanteService service;

	public ComprobanteController(ComprobanteRepository repo, ComprobanteService service) {
		this.repo = repo;
		this.service = service;
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

	@PostMapping("/generar")
	public ComprobanteResponse generar(@RequestBody GenerarComprobanteRequest req) {
		return service.generar(req.comandaId(), req.tipo(), req.clienteId());
	}
}

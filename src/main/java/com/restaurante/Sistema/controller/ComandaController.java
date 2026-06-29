package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.repository.ComandaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comandas")
public class ComandaController {

	private final ComandaRepository repo;

	public ComandaController(ComandaRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Comanda> listarTodas() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Comanda buscarPorId(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}

	@GetMapping("/estado/{estado}")
	public List<Comanda> porEstado(@PathVariable String estado) {
		return repo.findByEstado(estado);
	}

	@PostMapping
	public Comanda crear(@RequestBody Comanda comanda) {
		return repo.save(comanda);
	}

	@PutMapping("/{id}")
	public Comanda actualizar(@PathVariable Long id, @RequestBody Comanda datos) {
		Comanda c = repo.findById(id).orElse(null);
		if (c == null)
			return null;
		c.setEstado(datos.getEstado());
		c.setTotal(datos.getTotal());
		return repo.save(c);
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id) {
		repo.deleteById(id);
	}
}
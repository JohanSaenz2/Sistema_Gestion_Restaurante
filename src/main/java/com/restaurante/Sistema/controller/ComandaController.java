package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.service.ComandaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comandas")
public class ComandaController {

	private final ComandaService service;

	public ComandaController(ComandaService service) {
		this.service = service;
	}

	@GetMapping
	public List<Comanda> listarTodas() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public Comanda buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@GetMapping("/estado/{estado}")
	public List<Comanda> porEstado(@PathVariable EstadoComanda estado) {
		return service.porEstado(estado);
	}

	@PostMapping
	public Comanda crear(@RequestBody Comanda comanda) {
		return service.crear(comanda);
	}

	@PutMapping("/{id}")
	public Comanda actualizar(@PathVariable Long id, @RequestBody Comanda datos) {
		return service.actualizar(id, datos);
	}
	
	@PutMapping("/{id}/cancelar")
	public Comanda cancelar(@PathVariable Long id) {
	    return service.cancelar(id);
	}
	@PutMapping("/{id}/avanzar")
	public Comanda avanzar(@PathVariable Long id) {
	    return service.avanzarEstado(id);
	}
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id) {
		service.eliminar(id);
	}
}

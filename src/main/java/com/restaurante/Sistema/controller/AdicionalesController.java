package com.restaurante.Sistema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Adicionales;
import com.restaurante.Sistema.repository.AdicionalesRepository;

@RestController
@RequestMapping("/api/adicionales")
public class AdicionalesController {
	private final AdicionalesRepository repo;

	public AdicionalesController(AdicionalesRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Adicionales> listarTodos(@RequestParam(required = false) String estacion) {
		if (estacion != null && !estacion.isBlank()) {
			return repo.findByAplicaA(estacion);
		}
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Adicionales buscarPorID(@PathVariable Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Adicional " + id + " no encontrado"));
	}

	@PostMapping
	public Adicionales create(@RequestBody Adicionales adi) {
		return repo.save(adi);
	}

	@PutMapping("/{id}")
	public Adicionales actualizar(@PathVariable Long id, @RequestBody Adicionales datos) {
		Adicionales a = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Adicional " + id + " no encontrado"));
		a.setNombre(datos.getNombre());
		a.setPrecio(datos.getPrecio());
		a.setAplicaA(datos.getAplicaA());
		return repo.save(a);
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id) {
		repo.deleteById(id);
	}
}

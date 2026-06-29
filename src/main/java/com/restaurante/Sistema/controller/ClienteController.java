package com.restaurante.Sistema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.Sistema.model.Cliente;
import com.restaurante.Sistema.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteRepository repo;

	public ClienteController(ClienteRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Cliente> listarTodos() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Cliente buscarById(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}

	@PostMapping
	public Cliente crear(@RequestBody Cliente cliente) {
		return repo.save(cliente);
	}

	@PutMapping("/{id}")
	public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente datos) {
		Cliente c = repo.findById(id).orElse(null);
		if (c == null)
			return null;
		c.setNombre(datos.getNombre());
		c.setTelefono(datos.getTelefono());
		c.setEmail(datos.getEmail());
		c.setDireccion(datos.getDireccion());
		return repo.save(c);
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id) {
		repo.deleteById(id);
	}

}

package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Product;
import com.restaurante.Sistema.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

	private final ProductRepository repo;

	public ProductController(ProductRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<Product> listarTodos() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Product buscarPorId(@PathVariable Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
	}

	@PostMapping
	public Product crear(@RequestBody Product producto) {
		return repo.save(producto);
	}

	@PutMapping("/{id}")
	public Product actualizar(@PathVariable Long id, @RequestBody Product datos) {
		Product p = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
		p.setNombre(datos.getNombre());
		p.setPrecioBase(datos.getPrecioBase());
		p.setCategoria(datos.getCategoria());
		p.setEstacion(datos.getEstacion());
		return repo.save(p);
	}

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id) {
		repo.deleteById(id);
	}
}
package com.restaurante.Sistema.controller;

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

    // GET http://localhost:8080/api/productos
    @GetMapping
    public List<Product> listarTodos() {
        return repo.findAll();
    }

    // GET http://localhost:8080/api/productos/1
    @GetMapping("/{id}")
    public Product buscarPorId(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }
}
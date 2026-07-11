package com.restaurante.Sistema.service;

import com.restaurante.Sistema.model.Product;
import com.restaurante.Sistema.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductRepository repo;

    public ProductoService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listarTodos() {
        return repo.findAll();
    }

    public Product buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Product guardar(Product producto) {
        return repo.save(producto);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
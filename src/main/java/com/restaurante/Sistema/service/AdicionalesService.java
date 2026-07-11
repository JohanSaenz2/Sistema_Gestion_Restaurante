package com.restaurante.Sistema.service;

import com.restaurante.Sistema.model.Adicionales;
import com.restaurante.Sistema.repository.AdicionalesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdicionalesService {

    private final AdicionalesRepository repo;

    public AdicionalesService(AdicionalesRepository repo) {
        this.repo = repo;
    }

    public List<Adicionales> listarTodos() {
        return repo.findAll();
    }

    public Adicionales buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Adicionales guardar(Adicionales adicional) {
        return repo.save(adicional);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
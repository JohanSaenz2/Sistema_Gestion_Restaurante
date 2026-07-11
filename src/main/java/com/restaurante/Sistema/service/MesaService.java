package com.restaurante.Sistema.service;

import com.restaurante.Sistema.model.Mesa;
import com.restaurante.Sistema.repository.MesaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {

    private final MesaRepository repo;

    public MesaService(MesaRepository repo) {
        this.repo = repo;
    }

    public List<Mesa> listarTodas() {
        return repo.findAll();
    }

    public Mesa buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Mesa guardar(Mesa mesa) {
        return repo.save(mesa);
    }

    public Mesa actualizar(Long id, Mesa datos) {
        Mesa mesa = repo.findById(id).orElse(null);
        if (mesa == null) return null;
        mesa.setNumero(datos.getNumero());
        mesa.setEstado(datos.getEstado());
        return repo.save(mesa);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
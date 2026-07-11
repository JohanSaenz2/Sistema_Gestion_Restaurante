package com.restaurante.Sistema.service;

import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public List<Empleado> listarTodos() {
        return repo.findAll();
    }

    public Empleado buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Empleado guardar(Empleado empleado) {
        return repo.save(empleado);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
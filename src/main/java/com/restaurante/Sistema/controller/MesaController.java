package com.restaurante.Sistema.controller;
import com.restaurante.Sistema.model.Mesa;
import com.restaurante.Sistema.repository.MesaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaRepository repo;
    public MesaController(MesaRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Mesa> listarTodas() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Mesa buscarPorId(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Mesa crear(@RequestBody Mesa mesa) { return repo.save(mesa); }

    @PutMapping("/{id}")
    public Mesa actualizar(@PathVariable Long id, @RequestBody Mesa datos) {
        Mesa mesa = repo.findById(id).orElse(null);
        if (mesa == null) return null;
        mesa.setNumMesa(datos.getNumMesa());
        mesa.setEstado(datos.getEstado());
        return repo.save(mesa);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repo.deleteById(id); }
}
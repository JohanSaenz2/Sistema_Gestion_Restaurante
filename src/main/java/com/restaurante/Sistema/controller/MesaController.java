package com.restaurante.Sistema.controller;
import com.restaurante.Sistema.dto.MesaResponse;
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
    public List<MesaResponse> listarTodas() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public MesaResponse buscarPorId(@PathVariable Long id) {
        return repo.findById(id).map(this::toResponse).orElse(null);
    }

    @PostMapping
    public MesaResponse crear(@RequestBody Mesa mesa) {
        return toResponse(repo.save(mesa));
    }

    @PutMapping("/{id}")
    public MesaResponse actualizar(@PathVariable Long id, @RequestBody Mesa datos) {
        Mesa mesa = repo.findById(id).orElse(null);
        if (mesa == null) return null;
        mesa.setNumero(datos.getNumero());
        mesa.setEstado(datos.getEstado());
        return toResponse(repo.save(mesa));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repo.deleteById(id); }

    private MesaResponse toResponse(Mesa m) {
        return new MesaResponse(m.getMesaId(), m.getNumero(), m.getEstado());
    }
}

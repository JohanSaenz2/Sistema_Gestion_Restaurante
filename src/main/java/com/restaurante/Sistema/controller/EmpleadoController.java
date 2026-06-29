package com.restaurante.Sistema.controller;
import com.restaurante.Sistema.model.Empledado;
import com.restaurante.Sistema.repository.EmpleadoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoRepository repo;
    public EmpleadoController(EmpleadoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Empledado> listarTodos() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Empledado buscarPorId(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Empledado crear(@RequestBody Empledado empleado) {
        return repo.save(empleado);
    }

    @PutMapping("/{id}")
    public Empledado actualizar(@PathVariable Long id, @RequestBody Empledado datos) {
        Empledado emp = repo.findById(id).orElse(null);
        if (emp == null) return null;
        emp.setNombre(datos.getNombre());
        emp.setRol(datos.getRol());
        return repo.save(emp);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repo.deleteById(id); }
}
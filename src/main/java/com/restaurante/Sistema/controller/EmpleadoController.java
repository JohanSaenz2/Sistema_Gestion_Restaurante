package com.restaurante.Sistema.controller;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.repository.EmpleadoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoRepository repo;
    public EmpleadoController(EmpleadoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Empleado> listarTodos() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Empleado buscarPorId(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return repo.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado datos) {
        Empleado emp = repo.findById(id).orElse(null);
        if (emp == null) return null;
        emp.setNombre(datos.getNombre());
        emp.setRol(datos.getRol());
        return repo.save(emp);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repo.deleteById(id); }
}
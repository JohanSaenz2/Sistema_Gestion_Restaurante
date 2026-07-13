package com.restaurante.Sistema.controller;
import com.restaurante.Sistema.dto.EmpleadoResponse;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
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
    public List<EmpleadoResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public EmpleadoResponse buscarPorId(@PathVariable Long id) {
        return repo.findById(id).map(this::toResponse)
            .orElseThrow(() -> new RecursoNoEncontradoException("Empleado " + id + " no encontrado"));
    }

    @PostMapping
    public EmpleadoResponse crear(@RequestBody Empleado empleado) {
        return toResponse(repo.save(empleado));
    }

    @PutMapping("/{id}")
    public EmpleadoResponse actualizar(@PathVariable Long id, @RequestBody Empleado datos) {
        Empleado emp = repo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Empleado " + id + " no encontrado"));
        emp.setNombre(datos.getNombre());
        emp.setRol(datos.getRol());
        return toResponse(repo.save(emp));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repo.deleteById(id); }

    private EmpleadoResponse toResponse(Empleado e) {
        return new EmpleadoResponse(e.getId(), e.getNombre(), e.getUsuario(), e.getRol());
    }
}

// service/ComandaService.java
package com.restaurante.Sistema.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.Enum.EstadoMesa;
import com.restaurante.Sistema.dto.ComandaRequest;
import com.restaurante.Sistema.dto.ComandaResponse;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.model.Empleado;
import com.restaurante.Sistema.model.Mesa;
import com.restaurante.Sistema.repository.ComandaRepository;
import com.restaurante.Sistema.repository.EmpleadoRepository;
import com.restaurante.Sistema.repository.MesaRepository;

@Service
public class ComandaService {

	private final ComandaRepository repo;
	private final MesaRepository mesaRepo;
	private final EmpleadoRepository emplRepo;

	public ComandaService(ComandaRepository repo, MesaRepository mesaRepo, EmpleadoRepository emplRepo) {
		this.repo = repo;
		this.mesaRepo = mesaRepo;
		this.emplRepo = emplRepo;
	}

	public List<ComandaResponse> listarTodas() {
		return repo.findAll().stream().map(this::toResponse).toList();
	}

	public Comanda buscarPorId(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comanda " + id + " no encontrada"));
	}

	public List<Comanda> porEstado(EstadoComanda estado) {
		return repo.findByEstado(estado);
	}

	public ComandaResponse crear(ComandaRequest comanda2) {

		Mesa mesa = mesaRepo.findById(comanda2.mesaId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Mesa " + comanda2.mesaId() + " no encontrada"));
		Empleado empleado = emplRepo.findById(comanda2.empleadoId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Empleado " + comanda2.empleadoId() + " no encontrado"));

		Comanda comanda = new Comanda();
		comanda.setMesa(mesa);
		comanda.setEmpleado(empleado);
		comanda.setEstado(EstadoComanda.pendiente);
		comanda.setFecha(LocalDateTime.now());
		comanda.setTotal(BigDecimal.ZERO);

		mesa.setEstado(EstadoMesa.ocupada);
		mesaRepo.save(mesa);

		Comanda guardada = repo.save(comanda);

		return toResponse(guardada);
	}

	private ComandaResponse toResponse(Comanda c) {
		return new ComandaResponse(c.getId(), c.getEstado(), c.getTotal(), c.getFecha(), c.getMesa().getMesaId(),
				c.getMesa().getNumero(), c.getEmpleado().getId(), c.getEmpleado().getNombre());
	}

	public Comanda avanzarEstado(Long id) {
		Comanda comanda = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comanda " + id + " no encontrada"));

		switch (comanda.getEstado()) {
		case pendiente -> comanda.setEstado(EstadoComanda.entregada);
		case entregada -> {
			comanda.setEstado(EstadoComanda.pagada);
			liberarMesa(comanda.getMesa());
		}
		default ->
			throw new IllegalArgumentException("No se puede avanzar una comanda en estado: " + comanda.getEstado());
		}

		return repo.save(comanda);
	}

	public Comanda cancelar(Long id) {
		Comanda comanda = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comanda " + id + " no encontrada"));

		if (comanda.getEstado() == EstadoComanda.entregada) {
			throw new IllegalArgumentException("No se puede cancelar una comanda ya entregada");
		}

		comanda.setEstado(EstadoComanda.cancelada);
		liberarMesa(comanda.getMesa());
		return repo.save(comanda);
	}

	private void liberarMesa(Mesa mesa) {
		if (mesa != null) {
			mesa.setEstado(EstadoMesa.libre);
			mesaRepo.save(mesa);
		}
	}

	public void eliminar(Long id) {
		repo.deleteById(id);
	}

	public Comanda actualizar(Long id, Comanda datos) {
		Comanda c = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comanda " + id + " no encontrada"));
		c.setEstado(datos.getEstado());
		c.setTotal(datos.getTotal());
		return repo.save(c);
	}

}
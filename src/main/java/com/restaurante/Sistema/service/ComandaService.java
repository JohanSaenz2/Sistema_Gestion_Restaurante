// service/ComandaService.java
package com.restaurante.Sistema.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.Enum.EstadoMesa;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.model.Mesa;
import com.restaurante.Sistema.repository.ComandaRepository;
import com.restaurante.Sistema.repository.MesaRepository;

@Service
public class ComandaService {

	private final ComandaRepository repo;
	private final MesaRepository mesaRepo;

	public ComandaService(ComandaRepository repo, MesaRepository mesaRepo) {
		this.repo = repo;
		this.mesaRepo = mesaRepo;
	}

	public List<Comanda> listar() {
		return repo.findAll();
	}

	public Comanda buscarPorId(Long id) {
		return repo.findById(id).orElse(null);
	}

	public List<Comanda> porEstado(EstadoComanda estado) {
		return repo.findByEstado(estado);
	}

	public Comanda crear(Comanda comanda) {
		comanda.setEstado(EstadoComanda.pendiente);
		comanda.setFecha(LocalDateTime.now());

		Mesa mesa = mesaRepo.findById(comanda.getMesa().getMesaId()).orElse(null);

		if (mesa != null) {
			mesa.setEstado(EstadoMesa.ocupada);
			mesaRepo.save(mesa);
		}

		return repo.save(comanda);
	}

	public Comanda avanzarEstado(Long id) {
		Comanda comanda = repo.findById(id).orElse(null);
		if (comanda == null)
			return null;

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
		Comanda comanda = repo.findById(id).orElse(null);
		if (comanda == null)
			return null;

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
		Comanda c = repo.findById(id).orElse(null);
		if (c == null) {
			return null;
		}
		c.setEstado(datos.getEstado());
		c.setTotal(datos.getTotal());
		return repo.save(c);
	}

}
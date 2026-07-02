package com.restaurante.Sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.Sistema.Enum.EstadoComanda;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.repository.ComandaRepository;

@Service
public class ComandaService {

	private final ComandaRepository repo;

	public ComandaService(ComandaRepository repo) {
		this.repo = repo;
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
		return repo.save(comanda);
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

	public void eliminar(Long id) {
		repo.deleteById(id);
	}
}

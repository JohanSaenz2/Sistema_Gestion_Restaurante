package com.restaurante.Sistema.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.patrones.observer.EventoCambioEstado;
import com.restaurante.Sistema.patrones.observer.ISujeto;
import com.restaurante.Sistema.patrones.state.ContextoItem;
import com.restaurante.Sistema.repository.DetalleComandaRepository;

@Service
@Transactional
public class DetalleComandaService {

	private final DetalleComandaRepository repo;
	private final ISujeto publicador;

	public DetalleComandaService(DetalleComandaRepository repo, ISujeto publicador) {
		this.repo = repo;
		this.publicador = publicador;
	}

	public DetalleComanda enviarACocina(Long id) {
		return aplicar(id, ContextoItem::enviarACocina);
	}

	public DetalleComanda marcarListo(Long id) {
		return aplicar(id, ContextoItem::marcarListo);
	}

	public DetalleComanda entregar(Long id) {
		return aplicar(id, ContextoItem::entregar);
	}

	public DetalleComanda cancelar(Long id) {
		return aplicar(id, ContextoItem::cancelar);
	}

	private DetalleComanda aplicar(Long id, Consumer<ContextoItem> accion) {
		DetalleComanda detalle = repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe el detalle " + id));

		ContextoItem contexto = new ContextoItem(detalle.getEstado());
		accion.accept(contexto);

		EstadoItem nuevo = contexto.getEstadoItem();
		detalle.setEstado(nuevo);
		DetalleComanda guardado = repo.save(detalle);

		publicador.notificar(new EventoCambioEstado(
				guardado.getId(),
				guardado.getComanda().getId(),
				nuevo,
				guardado.getProducto().getEstacion()));

		return guardado;
	}
}

package com.restaurante.Sistema.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.model.Adicionales;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.model.Product;
import com.restaurante.Sistema.patrones.decorator.AdicionalConcret;
import com.restaurante.Sistema.patrones.decorator.ItemPedido;
import com.restaurante.Sistema.patrones.decorator.ProductBase;
import com.restaurante.Sistema.patrones.observer.EventoCambioEstado;
import com.restaurante.Sistema.patrones.observer.ISujeto;
import com.restaurante.Sistema.patrones.state.ContextoItem;
import com.restaurante.Sistema.repository.AdicionalesRepository;
import com.restaurante.Sistema.repository.DetalleComandaRepository;
import com.restaurante.Sistema.repository.ProductRepository;

@Service
@Transactional
public class DetalleComandaService {

	private final DetalleComandaRepository repo;
	private final ISujeto publicador;
	private final ProductRepository productRepo;
	private final AdicionalesRepository adicionalRepo;

	

	public DetalleComandaService(DetalleComandaRepository repo, ISujeto publicador, ProductRepository productRepo,
			AdicionalesRepository adicionalRepo) {
		this.repo = repo;
		this.publicador = publicador;
		this.productRepo = productRepo;
		this.adicionalRepo = adicionalRepo;
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

	public DetalleComanda createDetalle(Long idProduct, int cant, List<Long> adicionalesID) {
		Product product = productRepo.findById(idProduct).orElse(null);
		if (product == null)
			return null;

		ItemPedido item = new ProductBase(product);
		StringBuilder notes = new StringBuilder();
		if (adicionalesID != null) {
			for (Long id : adicionalesID) {
				Adicionales adi = adicionalRepo.findById(id).orElse(null);
				if (adi != null) {
					item = new AdicionalConcret(item, adi);
					notes.append(adi.getNombre()).append(", ");
				}

			}
		}
		BigDecimal priceAdi = item.getPrice();
		BigDecimal subTotal = priceAdi.multiply(BigDecimal.valueOf(cant));

		DetalleComanda dtComanda = new DetalleComanda();
		dtComanda.setProducto(product);
		dtComanda.setCantidad(cant);
		dtComanda.setSubtotal(subTotal);
		dtComanda.setNotas(notes.toString());
		dtComanda.setEstado(EstadoItem.pendiente);

		return repo.save(dtComanda);
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

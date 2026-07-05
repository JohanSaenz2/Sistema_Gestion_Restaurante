// service/DetalleComandaService.java
package com.restaurante.Sistema.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.dto.DetalleComandaResponse;
import com.restaurante.Sistema.model.Adicionales;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.model.Product;
import com.restaurante.Sistema.patrones.decorator.AdicionalConcret;
import com.restaurante.Sistema.patrones.decorator.ItemPedido;
import com.restaurante.Sistema.patrones.decorator.ProductBase;
import com.restaurante.Sistema.patrones.observer.EventoCambioEstado;
import com.restaurante.Sistema.patrones.observer.PublicadorEventosItem;
import com.restaurante.Sistema.patrones.state.ContextoItem;
import com.restaurante.Sistema.repository.AdicionalesRepository;
import com.restaurante.Sistema.repository.ComandaRepository;
import com.restaurante.Sistema.repository.DetalleComandaRepository;
import com.restaurante.Sistema.repository.ProductRepository;

@Service
public class DetalleComandaService {

	private final ProductRepository productoRepo;
	private final AdicionalesRepository adicionalesRepo;
	private final DetalleComandaRepository detalleRepo;
	private final PublicadorEventosItem publicador;
	private final ComandaRepository comandaRepo;

	public DetalleComandaService(ProductRepository productoRepo, AdicionalesRepository adicionalesRepo,
			DetalleComandaRepository detalleRepo, PublicadorEventosItem publicador, ComandaRepository comandaRepo) {
		super();
		this.productoRepo = productoRepo;
		this.adicionalesRepo = adicionalesRepo;
		this.detalleRepo = detalleRepo;
		this.publicador = publicador;
		this.comandaRepo = comandaRepo;
	}

	public DetalleComandaResponse crearDetalle(Long productoId, Long comandaId, int cantidad, List<Long> adicionalIds) {

		Product producto = productoRepo.findById(productoId).orElse(null);
		if (producto == null)
			return null;

		ItemPedido item = new ProductBase(producto);

		StringBuilder notas = new StringBuilder();
		if (adicionalIds != null) {
			for (Long adicionalId : adicionalIds) {
				Adicionales adicional = adicionalesRepo.findById(adicionalId).orElse(null);
				if (adicional != null) {
					item = new AdicionalConcret(item, adicional);
					notas.append(adicional.getNombre()).append(", ");
				}
			}
		}
		Comanda comanda = comandaRepo.findById(comandaId).orElse(null);
		if (comanda == null)
			return null;
		BigDecimal precioFinal = item.getPrice();
		BigDecimal subtotal = precioFinal.multiply(BigDecimal.valueOf(cantidad));

		DetalleComanda detalle = new DetalleComanda();
		detalle.setComanda(comanda);
		detalle.setProductoId(producto);
		detalle.setCantidad(cantidad);
		detalle.setSubtotal(subtotal);
		detalle.setNotas(notas.toString());
		detalle.setEstado(EstadoItem.pendiente);

		DetalleComanda guardado = detalleRepo.save(detalle);
		return new DetalleComandaResponse(
		    guardado.getId(),
		    guardado.getComanda().getId(),
		    guardado.getProductoId().getId(),
		    guardado.getProductoId().getNombre(),
		    guardado.getCantidad(),
		    guardado.getSubtotal(),
		    guardado.getNotas(),
		    guardado.getEstado()
		);
	}

	
	public DetalleComandaResponse enviarACocina(Long detalleId) {
		return cambiarEstado(detalleId, "cocina");
	}

	public DetalleComandaResponse marcarListo(Long detalleId) {
		return cambiarEstado(detalleId, "listo");
	}

	public DetalleComandaResponse entregar(Long detalleId) {
		return cambiarEstado(detalleId, "entregar");
	}

	public DetalleComandaResponse cancelarItem(Long detalleId) {
		return cambiarEstado(detalleId, "cancelar");
	}
	
	private DetalleComandaResponse cambiarEstado(Long detalleId, String accion) {
	    DetalleComanda detalle = detalleRepo.findById(detalleId).orElse(null);
	    if (detalle == null) return null;

	    ContextoItem contexto = new ContextoItem(detalle.getEstado());

	    switch (accion) {
	        case "cocina"   -> contexto.enviarACocina();
	        case "listo"    -> contexto.marcarListo();
	        case "entregar" -> contexto.entregar();
	        case "cancelar" -> contexto.cancelar();
	    }

	    detalle.setEstado(contexto.getEstadoItem());
	    detalleRepo.save(detalle);

	    publicador.notificar(new EventoCambioEstado(
	        detalle.getId(),
	        detalle.getComanda().getId(),
	        detalle.getEstado(),
	        detalle.getProductoId().getEstacion()
	    ));

	    return new DetalleComandaResponse(
	        detalle.getId(),
	        detalle.getComanda().getId(),
	        detalle.getProductoId().getId(),
	        detalle.getProductoId().getNombre(),
	        detalle.getCantidad(),
	        detalle.getSubtotal(),
	        detalle.getNotas(),
	        detalle.getEstado()
	    );
	}}
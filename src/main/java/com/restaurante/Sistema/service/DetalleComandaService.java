package com.restaurante.Sistema.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.model.Adicionales;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.model.Product;
import com.restaurante.Sistema.patrones.decorator.AdicionalConcret;
import com.restaurante.Sistema.patrones.decorator.ItemPedido;
import com.restaurante.Sistema.patrones.decorator.ProductBase;
import com.restaurante.Sistema.repository.AdicionalesRepository;
import com.restaurante.Sistema.repository.DetalleComandaRepository;
import com.restaurante.Sistema.repository.ProductRepository;

@Service
public class DetalleComandaService {
	private final ProductRepository productRepo;
	private final AdicionalesRepository adicionalRepo;
	private final DetalleComandaRepository detaComandaRepo;

	public DetalleComandaService(ProductRepository productRepo, AdicionalesRepository adicionalRepo,
			DetalleComandaRepository detaComandaRepo) {
		this.productRepo = productRepo;
		this.adicionalRepo = adicionalRepo;
		this.detaComandaRepo = detaComandaRepo;
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

		return detaComandaRepo.save(dtComanda);

	}
}

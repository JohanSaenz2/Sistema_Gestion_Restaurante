package com.restaurante.Sistema.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.config.ImpuestoConfig;
import com.restaurante.Sistema.dto.ComprobanteResponse;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Cliente;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.patrones.factory.ComprobanteFactory;
import com.restaurante.Sistema.patrones.factory.FactoryGen;
import com.restaurante.Sistema.repository.ClienteRepository;
import com.restaurante.Sistema.repository.ComandaRepository;
import com.restaurante.Sistema.repository.ComprobanteRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ComprobanteService {

	private final FactoryGen factory;
	private final ImpuestoConfig impuestoConfig;
	private final ComprobanteRepository comprobanteRepo;
	private final ComandaRepository comandaRepo;
	private final ClienteRepository clienteRepo;

	public ComprobanteService(FactoryGen factory, ImpuestoConfig impuestoConfig, ComprobanteRepository comprobanteRepo,
			ComandaRepository comandaRepo, ClienteRepository clienteRepo) {
		this.factory = factory;
		this.impuestoConfig = impuestoConfig;
		this.comprobanteRepo = comprobanteRepo;
		this.comandaRepo = comandaRepo;
		this.clienteRepo = clienteRepo;
	}

	@Transactional
	public ComprobanteResponse generar(Long comandaId, TipoComprobante tipo, Long clienteId, BigDecimal ivaPorcentaje) {
		Comanda comanda = comandaRepo.findById(comandaId)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comanda " + comandaId + " no encontrada"));

		Cliente cliente = clienteId == null ? null
				: clienteRepo.findById(clienteId)
						.orElseThrow(() -> new RecursoNoEncontradoException("Cliente " + clienteId + " no encontrado"));

		ComprobanteFactory f = factory.crear(tipo.name());
		f.setTasaIva(ivaPorcentaje != null ? ivaPorcentaje : impuestoConfig.getIva());

		Comprobante c = f.crear(comanda, cliente);
		comprobanteRepo.save(c);
		return new ComprobanteResponse(c.getId(), c.getComanda().getId(),
				c.getCliente() != null ? c.getCliente().getClienteID() : null,
				c.getCliente() != null ? c.getCliente().getNombre() : "Consumidor final",
				c.getCliente() != null ? c.getCliente().getIdentificacion() : null, c.getTipo(), c.getNumero(),
				c.getSubtotal(), c.getDescuento(), c.getIva(), c.getPropina(), c.getTotal(), c.getFechaEmision());
	}
}

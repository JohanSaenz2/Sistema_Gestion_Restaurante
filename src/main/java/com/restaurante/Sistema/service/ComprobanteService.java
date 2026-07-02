package com.restaurante.Sistema.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.config.ImpuestoConfig;
import com.restaurante.Sistema.exception.ComandaInvalidaException;
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

	private static final String PAQUETE_FACTORIES = "com.restaurante.Sistema.patrones.factory";

	private final FactoryGen factory;
	private final ImpuestoConfig impuestoConfig;
	private final ComprobanteRepository comprobanteRepo;
	private final ComandaRepository comandaRepo;
	private final ClienteRepository clienteRepo;

	public ComprobanteService(FactoryGen factory,
			ImpuestoConfig impuestoConfig,
			ComprobanteRepository comprobanteRepo,
			ComandaRepository comandaRepo,
			ClienteRepository clienteRepo) {
		this.factory = factory;
		this.impuestoConfig = impuestoConfig;
		this.comprobanteRepo = comprobanteRepo;
		this.comandaRepo = comandaRepo;
		this.clienteRepo = clienteRepo;
	}

	@PostConstruct
	void registrarFactories() {
		factory.init(PAQUETE_FACTORIES);
	}

	@Transactional
	public Comprobante generar(Long comandaId, TipoComprobante tipo, Long clienteId) {
		Comanda comanda = comandaRepo.findById(comandaId)
				.orElseThrow(() -> new ComandaInvalidaException("No existe la comanda " + comandaId));

		Cliente cliente = clienteId == null ? null
				: clienteRepo.findById(clienteId).orElse(null);

		ComprobanteFactory f = factory.crear(tipo.name());
		f.setTasaIva(impuestoConfig.getIva());

		Comprobante comprobante = f.crear(comanda, cliente);
		return comprobanteRepo.save(comprobante);
	}
}

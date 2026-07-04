package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.dto.DetalleComandaRequest;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.service.DetalleComandaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles")
public class DetalleComandaController {

	private final DetalleComandaService service;

	public DetalleComandaController(DetalleComandaService service) {
		this.service = service;
	}

	@PostMapping
	public DetalleComanda crear(@RequestBody DetalleComandaRequest req) {
		return service.createDetalle(req.productoId(), req.cantidad(), req.adicionalIds());
	}
}
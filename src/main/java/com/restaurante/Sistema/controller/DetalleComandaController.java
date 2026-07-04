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


	@PutMapping("/{id}/enviar-a-cocina")
	public DetalleComanda enviarACocina(@PathVariable Long id) {
		return service.enviarACocina(id);
	}

	@PutMapping("/{id}/marcar-listo")
	public DetalleComanda marcarListo(@PathVariable Long id) {
		return service.marcarListo(id);
	}

	@PutMapping("/{id}/entregar")
	public DetalleComanda entregar(@PathVariable Long id) {
		return service.entregar(id);
	}

	@PutMapping("/{id}/cancelar")
	public DetalleComanda cancelar(@PathVariable Long id) {
		return service.cancelar(id);
	}
}


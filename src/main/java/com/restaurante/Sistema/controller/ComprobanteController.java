package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.config.ImpuestoConfig;
import com.restaurante.Sistema.dto.ComprobanteResponse;
import com.restaurante.Sistema.dto.GenerarComprobanteRequest;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.service.ComprobanteService;
import com.restaurante.Sistema.service.PdfService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

	private final ComprobanteRepository repo;
	private final ComprobanteService service;
	private final PdfService pdfService;
	private final ImpuestoConfig impuestoConfig;

	public ComprobanteController(ComprobanteRepository repo, ComprobanteService service, PdfService pdfService,
			ImpuestoConfig impuestoConfig) {
		this.repo = repo;
		this.service = service;
		this.pdfService = pdfService;
		this.impuestoConfig = impuestoConfig;
	}

	@GetMapping
	public List<Comprobante> listarTodos() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Comprobante buscarPorId(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}

	@PostMapping
	public Comprobante crear(@RequestBody Comprobante comprobante) {
		return repo.save(comprobante);
	}

	@PostMapping("/generar")
	public ComprobanteResponse generar(@RequestBody GenerarComprobanteRequest req) {
		return service.generar(req.comandaId(), req.tipo(), req.clienteId(), req.ivaPorcentaje());
	}

	@GetMapping("/{id}/pdf")
	public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
		Comprobante c = repo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Comprobante " + id + " no encontrado"));
		byte[] pdf = pdfService.generarFacturaPdf(c, impuestoConfig.getIva());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=factura-" + c.getNumero() + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

}

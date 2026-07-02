package com.restaurante.Sistema.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.restaurante.Sistema.config.ImpuestoConfig;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

	private final ImpuestoConfig impuestoConfig;

	public ConfigController(ImpuestoConfig impuestoConfig) {
		this.impuestoConfig = impuestoConfig;
	}

	@GetMapping("/iva")
	public Map<String, BigDecimal> getIva() {
		return Map.of("iva", impuestoConfig.getIva());
	}

	@PutMapping("/iva")
	public Map<String, BigDecimal> setIva(@RequestBody Map<String, BigDecimal> body) {
		BigDecimal iva = body.get("iva");
		if (iva == null || iva.signum() < 0) {
			throw new IllegalArgumentException("La tasa de IVA debe ser un número mayor o igual a cero");
		}
		impuestoConfig.setIva(iva);
		return Map.of("iva", impuestoConfig.getIva());
	}
}

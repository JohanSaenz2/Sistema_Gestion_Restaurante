package com.restaurante.Sistema.patrones.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagoConDescuento implements StrategyPago {

	private final BigDecimal porcentajeDescuento; // ej: 0.10 para 10%
	private final String motivo; // "festivo", "promocion", "convenio"

	public PagoConDescuento(BigDecimal porcentajeDescuento, String motivo) {
		this.porcentajeDescuento = porcentajeDescuento;
		this.motivo = motivo;
	}

	@Override
	public BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal propina) {
		BigDecimal descuento = subtotal.multiply(porcentajeDescuento).setScale(2, RoundingMode.HALF_UP);
		return subtotal.subtract(descuento).add(propina);
	}

	@Override
	public String getNameMethod() {
		return "descuento_" + motivo;
	}

	public BigDecimal calcularDescuento(BigDecimal subtotal) {
		return subtotal.multiply(porcentajeDescuento).setScale(2, RoundingMode.HALF_UP);
	}

	public String getMotivo() {
		return motivo;
	}
}

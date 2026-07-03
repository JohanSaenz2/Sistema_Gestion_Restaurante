package com.restaurante.Sistema.service.pago;

import java.math.BigDecimal;

public class PagoEfectivo implements StrategyPago{

	@Override
	public BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal propina) {
		return subtotal.add(propina);
	}

	@Override
	public String getNameMethod() {
		return null;
	}

	
}

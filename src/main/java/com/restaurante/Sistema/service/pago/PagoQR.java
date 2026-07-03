package com.restaurante.Sistema.service.pago;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PagoQR implements StrategyPago {

	@Override
	public BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal propina) {
		return subtotal.add(propina);
	}

	@Override
	public String getNameMethod() {
		return "qr";
	}
}
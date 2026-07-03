package com.restaurante.Sistema.service.pago;

import java.math.BigDecimal;

public interface StrategyPago {
	BigDecimal calcularTotal (BigDecimal subtotal, BigDecimal propina);
	String getNameMethod();
}

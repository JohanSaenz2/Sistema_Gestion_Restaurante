package com.restaurante.Sistema.patrones.strategy;

import java.math.BigDecimal;

public interface StrategyPago {
	BigDecimal calcularTotal (BigDecimal subtotal, BigDecimal propina);
	String getNameMethod();
}

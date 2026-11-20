package com.restaurante.Sistema.patrones.strategy;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ContextoPago {

    // Solo los 3 métodos fijos se registran automáticamente
    private final Map<String, StrategyPago> estrategias;

    public ContextoPago(List<StrategyPago> listaEstrategias) {
        this.estrategias = listaEstrategias.stream()
            .collect(Collectors.toMap(
            		StrategyPago::getNameMethod,
                e -> e
            ));
    }

    public BigDecimal calcular(String metodo,
                               BigDecimal subtotal,
                               BigDecimal propina) {
    	StrategyPago estrategia = estrategias.get(metodo);
        if (estrategia == null) {
            throw new IllegalArgumentException(
                "Método de pago no reconocido: " + metodo
            );
        }
        return estrategia.calcularTotal(subtotal, propina);
    }

    public BigDecimal calcularConDescuento(BigDecimal subtotal,
                                           BigDecimal propina,
                                           BigDecimal porcentaje,
                                           String motivo) {
        PagoConDescuento estrategia = new PagoConDescuento(porcentaje, motivo);
        return estrategia.calcularTotal(subtotal, propina);
    }
}

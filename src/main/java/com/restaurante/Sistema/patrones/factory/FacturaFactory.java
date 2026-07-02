package com.restaurante.Sistema.patrones.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.anotacion.MiComponente;

@MiComponente(name = "factura")
public class FacturaFactory extends ComprobanteFactory {

	@Override
	public TipoComprobante getTipo() {
		return TipoComprobante.factura;
	}

	@Override
	protected String prefijoNumero() {
		return "FAC-";
	}

	@Override
	protected boolean requiereCliente() {
		return true;
	}

	@Override
	protected BigDecimal calcularIva(BigDecimal subtotal) {
		return subtotal.multiply(tasaIva).setScale(2, RoundingMode.HALF_UP);
	}
}

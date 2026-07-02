package com.restaurante.Sistema.patrones.factory;

import java.math.BigDecimal;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.anotacion.MiComponente;

@MiComponente(name = "nota_venta")
public class NotaVentaFactory extends ComprobanteFactory {

	@Override
	public TipoComprobante getTipo() {
		return TipoComprobante.nota_venta;
	}

	@Override
	protected String prefijoNumero() {
		return "NV-";
	}

	@Override
	protected boolean requiereCliente() {
		return false;
	}

	@Override
	protected BigDecimal calcularIva(BigDecimal subtotal) {
		return BigDecimal.ZERO;
	}
}

package com.restaurante.Sistema.patrones.factory;

import java.math.BigDecimal;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.anotacion.MiComponente;
import com.restaurante.Sistema.model.Cliente;

@MiComponente(name = "ticket_interno")
public class TicketInternoFactory extends ComprobanteFactory {

	@Override
	public TipoComprobante getTipo() {
		return TipoComprobante.ticket_interno;
	}

	@Override
	protected String prefijoNumero() {
		return "TI-";
	}

	@Override
	protected boolean requiereCliente() {
		return false;
	}

	@Override
	protected BigDecimal calcularIva(BigDecimal subtotal) {
		return BigDecimal.ZERO;
	}

	@Override
	protected Cliente clienteParaComprobante(Cliente cliente) {
		return null;
	}
}

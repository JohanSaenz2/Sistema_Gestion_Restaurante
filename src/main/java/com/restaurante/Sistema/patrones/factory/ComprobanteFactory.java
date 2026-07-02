package com.restaurante.Sistema.patrones.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.restaurante.Sistema.Enum.TipoComprobante;
import com.restaurante.Sistema.exception.ClienteRequeridoException;
import com.restaurante.Sistema.exception.ComandaInvalidaException;
import com.restaurante.Sistema.model.Cliente;
import com.restaurante.Sistema.model.Comanda;
import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.model.DetalleComanda;

public abstract class ComprobanteFactory {

	protected BigDecimal tasaIva = BigDecimal.ZERO;

	public void setTasaIva(BigDecimal tasaIva) {
		this.tasaIva = tasaIva;
	}

	public final Comprobante crear(Comanda comanda, Cliente cliente) {
		validarComanda(comanda);
		validarCliente(cliente);

		BigDecimal subtotal = calcularSubtotal(comanda);
		BigDecimal descuento = BigDecimal.ZERO;
		BigDecimal propina = BigDecimal.ZERO;
		BigDecimal iva = calcularIva(subtotal);
		BigDecimal total = subtotal.subtract(descuento).add(iva).add(propina);

		Comprobante comprobante = new Comprobante();
		comprobante.setComanda(comanda);
		comprobante.setCliente(clienteParaComprobante(cliente));
		comprobante.setTipo(getTipo());
		comprobante.setNumero(generarNumero(comanda));
		comprobante.setSubtotal(subtotal);
		comprobante.setDescuento(descuento);
		comprobante.setIva(iva);
		comprobante.setPropina(propina);
		comprobante.setTotal(total);
		comprobante.setFechaEmision(LocalDateTime.now());
		return comprobante;
	}

	public abstract TipoComprobante getTipo();

	protected abstract String prefijoNumero();

	protected abstract boolean requiereCliente();

	protected abstract BigDecimal calcularIva(BigDecimal subtotal);

	protected Cliente clienteParaComprobante(Cliente cliente) {
		return cliente;
	}

	protected BigDecimal calcularSubtotal(Comanda comanda) {
		return comanda.getDetalles().stream()
				.map(DetalleComanda::getSubtotal)
				.filter(s -> s != null)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	protected String generarNumero(Comanda comanda) {
		return prefijoNumero() + String.format("%06d", comanda.getId());
	}

	private void validarComanda(Comanda comanda) {
		if (comanda == null || comanda.getDetalles() == null || comanda.getDetalles().isEmpty()) {
			throw new ComandaInvalidaException("La comanda no existe o no tiene detalles");
		}
		if (calcularSubtotal(comanda).signum() <= 0) {
			throw new ComandaInvalidaException("El subtotal de la comanda debe ser mayor a cero");
		}
	}

	private void validarCliente(Cliente cliente) {
		if (requiereCliente() && (cliente == null || cliente.getIdentificacion() == null
				|| cliente.getIdentificacion().isBlank())) {
			throw new ClienteRequeridoException(
					"El comprobante " + getTipo() + " requiere un cliente con identificación");
		}
	}
}

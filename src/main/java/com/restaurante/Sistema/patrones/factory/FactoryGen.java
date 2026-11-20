package com.restaurante.Sistema.patrones.factory;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

@Component
public class FactoryGen {

	private final Map<String, Supplier<ComprobanteFactory>> componentes = Map.of("factura", FacturaFactory::new,
			"nota_venta", NotaVentaFactory::new, "ticket_interno", TicketInternoFactory::new);

	@SuppressWarnings("unchecked")
	public <T> T crear(String name) {
		Supplier<ComprobanteFactory> supplier = componentes.get(name);
		if (supplier == null) {
			throw new RuntimeException("Componente " + name + " no está registrado");
		}
		return (T) supplier.get();
	}

}

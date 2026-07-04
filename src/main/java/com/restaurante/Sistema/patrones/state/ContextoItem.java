package com.restaurante.Sistema.patrones.state;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import com.restaurante.Sistema.Enum.EstadoItem;

public class ContextoItem {

	private static final Map<EstadoItem, Supplier<IEstadoItem>> ESTADOS = new EnumMap<>(EstadoItem.class);

	static {
		ESTADOS.put(EstadoItem.pendiente, Pendiente::new);
		ESTADOS.put(EstadoItem.en_preparacion, EnPreparacion::new);
		ESTADOS.put(EstadoItem.listo, Listo::new);
		ESTADOS.put(EstadoItem.entregado, Entregado::new);
		ESTADOS.put(EstadoItem.cancelado, Cancelado::new);
	}

	private IEstadoItem estado;

	public ContextoItem(EstadoItem estadoInicial) {
		setEstado(resolver(estadoInicial));
	}

	private IEstadoItem resolver(EstadoItem estadoInicial) {
		Supplier<IEstadoItem> proveedor = ESTADOS.get(estadoInicial);
		if (proveedor == null) {
			throw new IllegalArgumentException("Estado de ítem no reconocido: " + estadoInicial);
		}
		return proveedor.get();
	}

	public void setEstado(IEstadoItem estado) {
		this.estado = estado;
		this.estado.setContexto(this);
	}

	public void enviarACocina() {
		estado.enviarACocina();
	}

	public void marcarListo() {
		estado.marcarListo();
	}

	public void entregar() {
		estado.entregar();
	}

	public void cancelar() {
		estado.cancelar();
	}

	public EstadoItem getEstadoItem() {
		return estado.getEstadoItem();
	}
}

package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public class Pendiente implements IEstadoItem {

	private ContextoItem contexto;

	@Override
	public void setContexto(ContextoItem contexto) {
		this.contexto = contexto;
	}

	@Override
	public void enviarACocina() {
		contexto.setEstado(new EnPreparacion());
	}

	@Override
	public void marcarListo() {
		throw new TransicionInvalidaException("No se puede marcar listo un ítem en estado pendiente");
	}

	@Override
	public void entregar() {
		throw new TransicionInvalidaException("No se puede entregar un ítem en estado pendiente");
	}

	@Override
	public void cancelar() {
		contexto.setEstado(new Cancelado());
	}

	@Override
	public EstadoItem getEstadoItem() {
		return EstadoItem.pendiente;
	}
}

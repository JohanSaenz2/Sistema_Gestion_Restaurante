package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public class Cancelado implements IEstadoItem {

	private ContextoItem contexto;

	@Override
	public void setContexto(ContextoItem contexto) {
		this.contexto = contexto;
	}

	@Override
	public void enviarACocina() {
		throw new TransicionInvalidaException("El ítem está cancelado");
	}

	@Override
	public void marcarListo() {
		throw new TransicionInvalidaException("El ítem está cancelado");
	}

	@Override
	public void entregar() {
		throw new TransicionInvalidaException("El ítem está cancelado");
	}

	@Override
	public void cancelar() {
		throw new TransicionInvalidaException("El ítem ya está cancelado");
	}

	@Override
	public EstadoItem getEstadoItem() {
		return EstadoItem.cancelado;
	}
}

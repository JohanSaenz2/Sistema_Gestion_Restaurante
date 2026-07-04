package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public class Entregado implements IEstadoItem {

	private ContextoItem contexto;

	@Override
	public void setContexto(ContextoItem contexto) {
		this.contexto = contexto;
	}

	@Override
	public void enviarACocina() {
		throw new TransicionInvalidaException("El ítem ya fue entregado");
	}

	@Override
	public void marcarListo() {
		throw new TransicionInvalidaException("El ítem ya fue entregado");
	}

	@Override
	public void entregar() {
		throw new TransicionInvalidaException("El ítem ya fue entregado");
	}

	@Override
	public void cancelar() {
		throw new TransicionInvalidaException("No se puede cancelar un ítem ya entregado");
	}

	@Override
	public EstadoItem getEstadoItem() {
		return EstadoItem.entregado;
	}
}
